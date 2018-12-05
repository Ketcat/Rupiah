package com.rupiah.flash.pro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rupiah.flash.pro.R;
import com.rupiah.flash.pro.utils.StatusBarUtils;
import com.rupiah.flash.pro.view.IdentifyingCodeView;

/**
 * 注册登陆界面
 **/
public class RegisterActivity extends BasicActivity implements View.OnClickListener {
    TextView tvPhoneNumber;//手机号
    TextView tvNoCode;//没有收到手机验证码
    IdentifyingCodeView identifyingCodeView;//输入验证码
    Button btRegister;//注册按钮

    @Override
    protected int getResourceId() {
        return R.layout.activity_register;
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
        tvNoCode = findViewById(R.id.tv_no_code);
        tvPhoneNumber = findViewById(R.id.register_phone_number);
        identifyingCodeView = findViewById(R.id.view_icv);
        btRegister = findViewById(R.id.register_bt);
    }

    @Override
    protected void initData() {
//        tvPhoneNumber.setText("+86" + getIntent().getStringExtra("phone"));
    }

    @Override
    protected void initListener() {
        tvNoCode.setOnClickListener(this);
        btRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_bt:
                startActivity(new Intent(RegisterActivity.this, SuccessActivity.class));
                finish();
                break;
            case R.id.tv_no_code:
                startActivity(new Intent(RegisterActivity.this, NoCodeActivity.class));
                finish();
                break;
            default:
                break;
        }
    }
}
