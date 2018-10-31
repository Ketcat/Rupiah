package com.ptyl.uang.flash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.ptyl.uang.flash.MainActivity;
import com.ptyl.uang.flash.R;
import com.ptyl.uang.flash.utils.StatusBarUtils;
/**
 * 注册成功倒计时界面
 * */
public class SuccessActivity extends BasicActivity {
    TextView timerTextView;

    @Override
    protected int getResourceId() {
        return R.layout.activity_success;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        StatusBarUtils.setWindowStatusBarColor(this, R.color.green, StatusBarUtils.Position.STATUS);
        timer.start();
    }

    @Override
    protected void initView() {
        timerTextView = findViewById(R.id.tv_timer);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    /**
     * 取消倒计时
     *
     * @param v
     */
    public void oncancel(View v) {
        timer.cancel();
    }

    /**
     * 开始倒计时
     *
     * @param v
     */
    public void restart(View v) {
        timer.start();
    }

    private CountDownTimer timer = new CountDownTimer(5000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            timerTextView.setText(((millisUntilFinished / 1000)+1) + "秒后跳转到我的页面");
        }

        @Override
        public void onFinish() {
            startActivity(new Intent(SuccessActivity.this, MainActivity.class));
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
