package com.ptyl.uang.flash.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ptyl.uang.flash.R;
import com.ptyl.uang.flash.utils.StatusBarUtils;

/**
 * 关于我们
 */
public class AboutUsActivity extends BasicActivity implements View.OnClickListener {
    RelativeLayout rlTitleBg;
    ImageView ivBack;
    TextView tvTitle;

    @Override
    protected int getResourceId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        StatusBarUtils.setWindowStatusBarColor(AboutUsActivity.this, R.color.green, StatusBarUtils.Position.STATUS);
    }

    @Override
    protected void initView() {
        rlTitleBg = findViewById(R.id.rl_title);
        ivBack = findViewById(R.id.iv_title_back);
        tvTitle = findViewById(R.id.tv_title);
    }

    @Override
    protected void initData() {
        rlTitleBg.setBackgroundResource(R.color.green);
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.about_us);
        tvTitle.setTextColor(getResources().getColor(R.color.white));
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
