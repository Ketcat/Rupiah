package com.flash.rupiah.pro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flash.rupiah.pro.R;
import com.flash.rupiah.pro.utils.StatusBarUtils;

/**
 * 隐私政策页面
 */
public class PrivacyActivity extends BasicActivity implements View.OnClickListener {
    RelativeLayout rlLoanAgreement;
    RelativeLayout rlPrivacyPolicy;
    ImageView ivBack;
    TextView tvTitle;

    @Override
    protected int getResourceId() {
        return R.layout.activity_privacy;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        StatusBarUtils.setWindowStatusBarColor(PrivacyActivity.this, R.color.tab_bg, StatusBarUtils.Position.STATUS);
    }

    @Override
    protected void initView() {
            ivBack = findViewById(R.id.iv_title_back);
            tvTitle = findViewById(R.id.tv_title);
            rlLoanAgreement = findViewById(R.id.rl_loan_agreement);
            rlPrivacyPolicy = findViewById(R.id.rl_privacy_policy);

    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.privacy);
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        rlPrivacyPolicy.setOnClickListener(this);
        rlLoanAgreement.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.rl_loan_agreement:
                startActivity(new Intent(PrivacyActivity.this,LoanAgreementActivity.class));
                break;
            case R.id.rl_privacy_policy:
                break;
            default:
                break;
        }
    }
}
