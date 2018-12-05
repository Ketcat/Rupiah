package com.rupiah.flash.pro.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rupiah.flash.pro.R;
import com.rupiah.flash.pro.utils.StatusBarUtils;

/**
 * 意见反馈
 */
public class OpinionActivity extends BasicActivity implements View.OnClickListener {
    EditText etOpinion, etPhoneNubmer;
    ImageView ivBack;
    TextView tvTitle;
    Button submit;

    @Override
    protected int getResourceId() {
        return R.layout.activity_opinion;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        StatusBarUtils.setWindowStatusBarColor(OpinionActivity.this, R.color.tab_bg, StatusBarUtils.Position.STATUS);
    }

    @Override
    protected void initView() {
        etOpinion = findViewById(R.id.et_opinion);
        etPhoneNubmer = findViewById(R.id.et_phone_number);
        submit = findViewById(R.id.bt_submit);
        ivBack = findViewById(R.id.iv_title_back);
        tvTitle = findViewById(R.id.tv_title);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.online);
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.bt_submit:
                String opinion = etOpinion.getText().toString().trim();
                String phoneNumber = etPhoneNubmer.getText().toString().trim();
                finish();
                break;
            default:
                break;
        }
    }
}
