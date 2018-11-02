package com.flash.rupiah.pro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flash.rupiah.pro.R;
import com.flash.rupiah.pro.utils.StatusBarUtils;

/**
 * 没有收到验证码的界面
 * */
public class NoCodeActivity extends BasicActivity implements View.OnClickListener {
    TextView tvPhoneNumber;//电话号码
    Button btSendAgain;//再次发送验证码
    RelativeLayout faceBookSend;//facebook验证方式
    RelativeLayout telePhoneSend;//电话验证方式
    @Override
    protected int getResourceId() {
        return R.layout.activity_no_code;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        StatusBarUtils.setWindowStatusBarColor(this, R.color.green, StatusBarUtils.Position.STATUS);
    }

    @Override
    protected void initView() {
        tvPhoneNumber = findViewById(R.id.tv_no_code);
        btSendAgain = findViewById(R.id.send_again_bt);
        faceBookSend = findViewById(R.id.facebook_method);
        telePhoneSend = findViewById(R.id.telephone_method);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        btSendAgain.setOnClickListener(this);
        telePhoneSend.setOnClickListener(this);
        faceBookSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_again_bt:
                startActivity(new Intent(NoCodeActivity.this,RegisterActivity.class));
                finish();
                break;
            case R.id.facebook_method:
                break;
            case R.id.telephone_method:
                break;
            default:
                break;
        }
    }
}
