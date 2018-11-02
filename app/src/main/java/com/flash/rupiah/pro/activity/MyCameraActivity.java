package com.flash.rupiah.pro.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.flash.rupiah.pro.R;
import com.flash.rupiah.pro.utils.Contants;
import com.flash.rupiah.pro.utils.SharedPreUtil;
import com.flash.rupiah.pro.view.LoadingCustom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyCameraActivity extends BasicActivity implements View.OnClickListener, Camera
        .AutoFocusCallback {
    private Camera.Parameters parameters = null;
    private Camera camera;
    SurfaceView surfaceView;
    ImageView view;
    ImageView cameraIv;
    ImageView testIv;
    int type = 0;
    String token;
    File file;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadingCustom.dismissprogress();
            switch (msg.what) {
                case 1:
                    if (file != null) {
                        upOrc(file);
                    }
                    break;
                case 2:
                    file.delete();
                    finish();
                    break;
            }
        }
    };

    @Override
    protected int getResourceId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_my_camera;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        type = getIntent().getIntExtra("type", 0);
    }

    @Override
    protected void initView() {
        surfaceView = (SurfaceView) this.findViewById(R.id.surfaceview);
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().setKeepScreenOn(true);// 屏幕常亮
        surfaceView.getHolder().addCallback(new SurfaceCallback());//为SurfaceView的句柄添加一个回调函数
        view = findViewById(R.id.iv_camera);
        testIv = findViewById(R.id.test_iv);
        cameraIv = findViewById(R.id.iv_camera_center);
        if (type == 0) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void initData() {
        token = SharedPreUtil.readString(this, SharedPreUtil.TOKEN, "");
    }

    @Override
    protected void initListener() {
        cameraIv.setOnClickListener(this);
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        camera.autoFocus(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_camera_center:
                camera.takePicture(null, null, new MyPictureCallback());
                cameraIv.setEnabled(false);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(0);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private final class MyPictureCallback implements Camera.PictureCallback {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            try {
                saveToSDCard(data);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    //
//    /**
//     * 将拍下来的照片存放在SD卡中
//     *
//     * @param data
//     * @throws IOException
//     */
    public void saveToSDCard(byte[] data) throws IOException {
        FileOutputStream bos = null;
        Bitmap bm = null;
        Bitmap bitmap = null;
        try {
            // 获得图片
            bm = BitmapFactory.decodeByteArray(data, 0, data.length);
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = "";
                if (type == 0) {
                    fileName = "personal.jpg";
                } else if (type == 1) {
                    fileName = "industry.jpg";
                } else if (type == 2) {
                    fileName = "family.jpg";
                } else if (type == 3) {
                    fileName = "other.jpg";
                }
                file = new File(appDir, fileName);
                bos = new FileOutputStream(file);
                bitmap = setImgSize(bm, 1920, 1080);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);//将图片压缩到流中
                bos.flush();//输出
                bos.close();//关闭
                bitmap.recycle();// 回收bitmap空间
                bm.recycle();// 回收bitmap空间
                camera.stopPreview();// 关闭预览
                upPhoto(file);
            } else {
                Toast.makeText(mContext, R.string.no_card, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private final class SurfaceCallback implements SurfaceHolder.Callback {

        // 拍照状态变化时调用该方法
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            parameters = camera.getParameters(); // 获取各项参数
            List<Camera.Size> pictureSizeList = parameters.getSupportedPictureSizes();
            /**从列表中选取合适的分辨率*/
            Camera.Size picSize = getProperSize(pictureSizeList, ((float) height / width));
            if (null == picSize) {
                picSize = parameters.getPictureSize();
            }
            // 根据选出的PictureSize重新设置SurfaceView大小
            float w = picSize.width;
            float h = picSize.height;
            parameters.setPictureSize(picSize.width, picSize.height);
            surfaceView.setLayoutParams(new FrameLayout.LayoutParams((int) (height * (h / w)),
                    height));

            // 获取摄像头支持的PreviewSize列表
            List<Camera.Size> previewSizeList = parameters.getSupportedPreviewSizes();

            Camera.Size preSize = getProperSize(previewSizeList, ((float) height) / width);
            if (null != preSize) {
                parameters.setPreviewSize(preSize.width, preSize.height);
            }

            parameters.setJpegQuality(100); // 设置照片质量
            if (parameters.getSupportedFocusModes().contains(Camera.Parameters
                    .FOCUS_MODE_CONTINUOUS_PICTURE)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 连续对焦模式
            }

            camera.cancelAutoFocus();//自动对焦。
            camera.setDisplayOrientation(90);// 设置PreviewDisplay的方向，效果就是将捕获的画面旋转多少度显示
            camera.setParameters(parameters);
        }

        // 开始拍照时调用该方法
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera = Camera.open(); // 打开摄像头
                camera.setPreviewDisplay(holder); // 设置用于显示拍照影像的SurfaceHolder对象
                camera.startPreview(); // 开始预览
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // 停止拍照时调用该方法
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.e("MyCameraActivity", "调用了释放");
            if (camera != null) {
                camera.stopPreview();
                camera.release(); // 释放照相机
                camera = null;
            }
        }
    }


    /**
     * 从列表中选取合适的分辨率
     * 默认w:h = 4:3
     * <p>注意：这里的w对应屏幕的height
     * h对应屏幕的width<p/>
     */

    private Camera.Size getProperSize(List<Camera.Size> pictureSizeList, float screenRatio) {
        Camera.Size result = null;
        for (Camera.Size size : pictureSizeList) {
            float currentRatio = ((float) size.width) / size.height;
            if (currentRatio - screenRatio == 0) {
                result = size;
                break;
            }
        }

        if (null == result) {
            for (Camera.Size size : pictureSizeList) {
                float curRatio = ((float) size.width) / size.height;
                if (curRatio == 4f / 3) {// 默认w:h = 4:3
                    result = size;
                    break;
                }
            }
        }

        return result;
    }

    private void upPhoto(final File file) {
        LoadingCustom.showprogress(this, "", true);
        String fileType = "";
        if (type == 0) {
            fileType = "KTP_PHOTO";
            SharedPreUtil.saveSring(mContext, SharedPreUtil.PERSONAL, file.getName());
        } else if (type == 1) {
            fileType = "EMPLOYMENT_PHOTO";
            SharedPreUtil.saveSring(mContext, SharedPreUtil.INDUSTRY, file.getName());
        } else if (type == 2) {
            fileType = "FAMILY_CARD_PHOTO";
            SharedPreUtil.saveSring(mContext, SharedPreUtil.FAMILY, file.getName());
        } else if (type == 3) {
            fileType = "OTHER_PHOTO";
            SharedPreUtil.saveSring(mContext, SharedPreUtil.OTHER, file.getName());
        }
        String url1 = Contants.FILES + "?fileType=" + fileType;
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody filebody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), filebody).build();
        Request request1 = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url(url1)
                .put(requestBody).build();
        Call call1 = okHttpClient.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("MyCameraActivity", e.getMessage() + "upPhoto失败");
                handler.sendEmptyMessage(2);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                Log.e("MyCameraActivity", respones + "upPhotoPost");
                if (type == 0) {
                    handler.sendEmptyMessage(1);
                } else  {
                    handler.sendEmptyMessage(2);
                }
            }
        });
    }

    private void upOrc(File file) {
        LoadingCustom.showprogress(this, "", true);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(3, TimeUnit
                .MINUTES).readTimeout(3, TimeUnit.MINUTES).build();
//        File file1 = Compressor.getDefault(this).compressToFile(file);
        RequestBody filebody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), filebody).build();
        Request request1 = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.OCR).put(requestBody).build();
        Call call1 = okHttpClient.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("MyCameraActivity", e.getMessage() + "upOrc失败");
                handler.sendEmptyMessage(2);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                Log.e("MyCameraActivity", respones + "upOrcPost");
                handler.sendEmptyMessage(2);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public Bitmap setImgSize(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高.
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例.
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
}
