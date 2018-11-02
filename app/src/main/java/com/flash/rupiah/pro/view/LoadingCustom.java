package com.flash.rupiah.pro.view;

import android.app.Dialog;
import android.content.Context;

import com.flash.rupiah.pro.R;

/**
 * Created by heise on 2018/6/5.
 */

public class LoadingCustom extends Dialog {
    private static LoadingCustom mLoadingProgress;

    public LoadingCustom(Context context) {
        super(context);

    }

    public LoadingCustom(Context context, int theme) {
        super(context, theme);
    }

    public static void showprogress(Context context, CharSequence message, boolean iscanCancel) {
        mLoadingProgress = new LoadingCustom(context, R.style.loading_dialog);//自定义style文件主要让背景变成透明并去掉标题部分<!-- 自定义loading dialog -->

//触摸外部无法取消,必须
        mLoadingProgress.setCanceledOnTouchOutside(false);

        mLoadingProgress.setTitle("");
        mLoadingProgress.setContentView(R.layout.loading_layout);
        mLoadingProgress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        if (message == null || TextUtils.isEmpty(message)) {
//            mLoadingProgress.findViewById(R.id.loading_tv).setVisibility(View.GONE);
//        } else {
//            TextView tv = (TextView) mLoadingProgress.findViewById(R.id.loading_tv);
//            tv.setText(message);
//        }
        //按返回键响应是否取消等待框的显示
        mLoadingProgress.setCancelable(true);

        mLoadingProgress.show();

    }

    public static void dismissprogress() {
        if (mLoadingProgress != null) {
            mLoadingProgress.dismiss();
        }
    }
}
