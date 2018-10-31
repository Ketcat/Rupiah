package com.ptyl.uang.flash.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ptyl.uang.flash.R;
import com.megvii.licensemanager.Manager;
import com.megvii.livenessdetection.LivenessLicenseManager;
import com.megvii.livenesslib.LivenessActivity;
import com.megvii.livenesslib.util.ConUtil;

import org.json.JSONObject;

import java.util.Map;

import static android.os.Build.VERSION_CODES.M;

public class FaceActivity extends BasicActivity implements View.OnClickListener {
    Button button;

    @Override
    protected int getResourceId() {
        return R.layout.activity_test_face;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        netWorkWarranty();
    }

    @Override
    protected void initView() {
        button = findViewById(R.id.test);
    }


    @Override
    protected void initData() {

    }

    private void checkID(int resID) {
        if (resID == R.string.verify_success) {
            doPlay(R.raw.meglive_success);
        } else if (resID == R.string.liveness_detection_failed_not_video) {
            doPlay(R.raw.meglive_failed);
        } else if (resID == R.string.liveness_detection_failed_timeout) {
            doPlay(R.raw.meglive_failed);
        } else if (resID == R.string.liveness_detection_failed) {
            doPlay(R.raw.meglive_failed);
        } else {
            doPlay(R.raw.meglive_failed);
        }
    }

    private MediaPlayer mMediaPlayer = null;

    private void doPlay(int rawId) {
        if (mMediaPlayer == null) mMediaPlayer = new MediaPlayer();

        mMediaPlayer.reset();
        try {
            AssetFileDescriptor localAssetFileDescriptor = getResources().openRawResourceFd(rawId);
            mMediaPlayer.setDataSource(localAssetFileDescriptor.getFileDescriptor(),
                    localAssetFileDescriptor.getStartOffset(), localAssetFileDescriptor.getLength
                            ());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception localIOException) {
            localIOException.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == EXTERNAL_STORAGE_REQ_CAMERA_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {// Permission Granted

                ConUtil.showToast(this, getResources().getString(R.string.permission_fail));
            } else
                enterNextPage();
        }
    }
    @Override
    protected void initListener() {
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test:
                requestCameraPerm();
                break;
            default:
                break;
        }
    }

    public static final int EXTERNAL_STORAGE_REQ_CAMERA_CODE = 10;

    private void requestCameraPerm() {
        if (android.os.Build.VERSION.SDK_INT >= M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED) {
                //进行权限请求
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        EXTERNAL_STORAGE_REQ_CAMERA_CODE);
            } else {
                enterNextPage();
            }
        } else {
            enterNextPage();
        }
    }

    private static final int PAGE_INTO_LIVENESS = 100;

    private void enterNextPage() {
        startActivityForResult(new Intent(FaceActivity.this, LivenessActivity.class), PAGE_INTO_LIVENESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAGE_INTO_LIVENESS && resultCode == RESULT_OK) {
//            String result = data.getStringExtra("result");
//            String delta = data.getStringExtra("delta");
//            Serializable images=data.getSerializableExtra("images");
            Bundle bundle = data.getExtras();
            doit(bundle);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
        }
    }

    String uuid;

    /**
     * 联网授权
     */
    private void netWorkWarranty() {
        uuid = ConUtil.getUUIDString(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Manager manager = new Manager(FaceActivity.this);
                LivenessLicenseManager licenseManager = new LivenessLicenseManager(FaceActivity
                        .this);
                manager.registerLicenseManager(licenseManager);
                manager.takeLicenseFromNetwork(uuid);
                if (licenseManager.checkCachedLicense() > 0) {
                    //授权成功
                    mHandler.sendEmptyMessage(1);
                } else {
                    //授权失败
                    mHandler.sendEmptyMessage(2);
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("log", "联网成功");
                    break;
                case 2:
                    Log.e("log", "联网失败");
                    break;
                default:
                    break;
            }
        }
    };

    public void doit(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        String resultOBJ = bundle.getString("result");
        try {
            JSONObject result = new JSONObject(resultOBJ);
            int resID = result.getInt("resultcode");
            checkID(resID);
            boolean isSuccess = result.getString("result").equals(getResources().getString(R
                    .string.verify_success));

            if (isSuccess) {
                String delta = bundle.getString("delta");
                Map<String, byte[]> images = (Map<String, byte[]>) bundle.getSerializable("images");
                byte[] image_best = images.get("image_best");
                byte[] image_env = images.get("image_env");
                //N张动作图根据需求自取，对应字段action1、action2 ...
                // byte[] image_action1 = images.get("image_action1);
//                imageView.setImageBitmap(BitmapFactory.decodeByteArray(image_best, 0, image_best
//                        .length));

                //保存图片
                //bestPath = ConUtil.saveJPGFile(this, image_best, "image_best");
                //envPath = ConUtil.saveJPGFile(this, image_env, "image_env");
                //调用活体比对API
//                imageVerify(images,delta);
            }
//            doRotate(isSuccess);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
