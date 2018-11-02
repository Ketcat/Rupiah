package com.flash.rupiah.pro.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.flash.rupiah.pro.R;
import com.flash.rupiah.pro.utils.Contants;
import com.flash.rupiah.pro.utils.StatusBarUtils;

public class LoginActivity extends BasicActivity implements View.OnClickListener {
    EditText etPhone;
    Button btNext;



    @Override
    protected int getResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        StatusBarUtils.setWindowStatusBarColor(this, R.color.tab_bg, StatusBarUtils.Position.STATUS);
    }

    @Override
    protected void initView() {
        etPhone = (EditText) findViewById(R.id.login_edit_phone);
        btNext = (Button) findViewById(R.id.login_bt_next);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        btNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_bt_next:
//                    startActivity(new Intent(LoginActivity.this, FaceBookLoginActivity.class));
//                startActivity(new Intent(LoginActivity.this, LoanChannelActivity.class));
//                startActivity(new Intent(LoginActivity.this, MyCameraActivity.class));

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
//                List<SmsInfo> list = PhoneInfo.getSmsFromPhone(LoginActivity.this);
//                if (list != null) {
//                    for (SmsInfo smsInfo : list) {
//                        Log.e("LoginActivity", smsInfo.toString());
//                    }
//                }
                finish();
                break;
            default:
                break;
        }
    }


    private void startToSetting() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("需要开启权限后才能使用")//设置对话框的标题
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, Contants.REQUEST_PERMISSION_SEETING);
                        dialog.dismiss();
                    }
                }).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }




//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        //如果是从设置界面返回,就继续判断权限
//        if (requestCode == Contants.REQUEST_PERMISSION_SEETING) {
//            checkPermiss();
//        }
//    }
//    public void getPermissionName() {
//
//        map = new HashMap<>();
//        map.put("android.permission.WRITE_CONTACTS", "修改联系人");
//        map.put("android.permission.GET_ACCOUNTS", "访问账户Gmail列表");
//        map.put("android.permission.READ_CONTACTS", "读取联系人");
//        map.put("android.permission.READ_CALL_LOG", "读取通话记录");
//        map.put("android.permission.READ_PHONE_STATE", "读取电话状态");
//        map.put("android.permission.CALL_PHONE", "拨打电话");
//        map.put("android.permission.WRITE_CALL_LOG", "修改通话记录");
//        map.put("android.permission.USE_SIP", "使用SIP视频");
//        map.put("android.permission.PROCESS_OUTGOING_CALLS", "PROCESS_OUTGOING_CALLS");
//        map.put("com.android.voicemail.permission.ADD_VOICEMAIL", "ADD_VOICEMAIL");
//        map.put("android.permission.READ_CALENDAR", "读取日历");
//        map.put("android.permission.WRITE_CALENDAR", "修改日历");
//        map.put("android.permission.CAMERA", "拍照");
//        map.put("android.permission.BODY_SENSORS", "传感器");
//        map.put("android.permission.ACCESS_FINE_LOCATION", "获取精确位置");
//        map.put("android.permission.ACCESS_COARSE_LOCATION", "获取粗略位置");
//        map.put("android.permission.READ_EXTERNAL_STORAGE", "读存储卡");
//        map.put("android.permission.WRITE_EXTERNAL_STORAGE", "修改存储卡");
//        map.put("android.permission.RECORD_AUDIO", "录音");
//        map.put("android.permission.READ_SMS", "读取短信内容");
//        map.put("android.permission.RECEIVE_WAP_PUSH", "接收Wap Push");
//        map.put("android.permission.RECEIVE_MMS", "接收短信");
//        map.put("android.permission.SEND_SMS", "发送短信");
//        map.put("android.permission.READ_CELL_BROADCASTS", "READ_CELL_BROADCASTS");
//
//
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
