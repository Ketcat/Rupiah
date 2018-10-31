package com.ptyl.uang.flash.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptyl.uang.flash.R;
import com.ptyl.uang.flash.utils.StatusBarUtils;

public class LoanAgreementActivity extends BasicActivity implements View.OnClickListener {
    ImageView ivBack;
    TextView tvTitle;

    @Override
    protected int getResourceId() {
        return R.layout.activity_loan_agreement;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        StatusBarUtils.setWindowStatusBarColor(LoanAgreementActivity.this, R.color.tab_bg, StatusBarUtils.Position.STATUS);
    }

    @Override
    protected void initView() {
        ivBack = findViewById(R.id.iv_title_back);
        tvTitle = findViewById(R.id.tv_title);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.loan_agreement);
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            default:
                break;
        }
    }
}
