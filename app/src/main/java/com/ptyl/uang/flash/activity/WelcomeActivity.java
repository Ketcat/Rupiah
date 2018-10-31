package com.ptyl.uang.flash.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ptyl.uang.flash.R;
import com.ptyl.uang.flash.utils.Contants;
import com.ptyl.uang.flash.utils.SharedPreUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author BloodFragance
 * @ClassName WelcomeActivity
 * @time 2017/1/25 20:30
 * @desc 欢迎页面
 */
public class WelcomeActivity extends BasicActivity {
    TextView tvTimer;
    private HashMap<String, String> map;//管理权限的map
    String[] permissions = new String[]{"android.permission.SEND_SMS", "android.permission" + ""
            + ".READ_CONTACTS", "android.permission.ACCESS_FINE_LOCATION", "android.permission" +
            "" + ".CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission" +
            "" + ".READ_PHONE_STATE", "android.permission.WRITE_CALL_LOG"};
    boolean isFirst = false;
    long lastTime = 0;
    String token = "";

    @Override
    protected int getResourceId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void init() {
        checkPermiss();
    }

    @Override
    protected void initView() {
//        DisplayMetrics metric = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metric);
//       int intwidth =metric.widthPixels;  //宽度（PX）
//        int intheight =metric.heightPixels;  //高度（PX）
//        float  floatdensity =metric.density;  //密度（0.75 / 1.0 / 1.5）
//        int  intdensityDpi =metric.densityDpi;  //密度DPI（120 / 160 / 240）
//        Log.e("midu",intwidth+"宽度"+intheight+"高度"+floatdensity+"密度"+intdensityDpi+"密度");
        tvTimer = findViewById(R.id.tv_timer);
    }

    @Override
    protected void initData() {
//        Log.e("log", System.currentTimeMillis() + "");
        isFirst = SharedPreUtil.readBoolean(mContext, SharedPreUtil.ISFIRST);
        lastTime = SharedPreUtil.readLong(mContext, SharedPreUtil.LASTLOGINTIME, 0);
//        lastTime = 1531804868780l;
        token = SharedPreUtil.readString(mContext, SharedPreUtil.TOKEN, "");
        if (lastTime > 0) {
//            Log.e("log", (System.currentTimeMillis() - lastTime) + "");
            long time = (System.currentTimeMillis() - lastTime) / (1000 * 60 * 60 * 24);
            if (time >= 20 && !TextUtils.isEmpty(token)) {
                SharedPreUtil.saveSring(mContext, SharedPreUtil.PHONE, "");
                SharedPreUtil.saveSring(mContext, SharedPreUtil.TOKEN, "");
                SharedPreUtil.saveBoolean(mContext, SharedPreUtil.ISLOGIN, false);
            }
        }
    }

    @Override
    protected void initListener() {

    }

    private CountDownTimer timer = new CountDownTimer(3000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tvTimer.setVisibility(View.VISIBLE);
            tvTimer.setText(((millisUntilFinished / 1000)) + "second");
        }

        @Override
        public void onFinish() {
            tvTimer.setVisibility(View.GONE);
            if (!isFirst) {
                SharedPreUtil.saveBoolean(mContext, SharedPreUtil.ISFIRST, true);
                startActivity(new Intent(WelcomeActivity.this, GuidActivity.class));
                finish();
            } else {
                startNewActivity();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    /**
     * 检测权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[]
            grantResults) {
        switch (requestCode) {
            case Contants.REQUEST_PERMISSION_CODE_TAKE_PIC:
                timer.start();
                break;
        }
    }

    /**
     * 请求权限
     */
    private void checkPermiss() {
        List<String> deniedPerms = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < permissions.length; i++) {
                if (PackageManager.PERMISSION_GRANTED != checkSelfPermission(permissions[i])) {
                    deniedPerms.add(permissions[i]);
                }
            }
            int denyPermNum = deniedPerms.size();
            if (denyPermNum != 0) {
                requestPermissions(deniedPerms.toArray((new String[denyPermNum])), Contants
                        .REQUEST_PERMISSION_CODE_TAKE_PIC);
            } else {
                timer.start();
            }
        } else {
            timer.start();
        }
    }
}
