package com.rupiah.flash.pro.utils;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author weichao
 * @ClassName StatusBarUtils
 * @time 2018/5/26
 * @desc 状态栏相关工具类
 */
public class StatusBarUtils {

    public enum Position {
        /**
         * 顶部导航栏
         */
        STATUS,
        /**
         * 底部导航栏
         */
        NAVIGATION,
        /**
         * 上下都需要
         */
        ALL
    }

    /**
     * 改变状态栏颜色
     *
     * @param activity   需要改变颜色的activity
     * @param colorResId 要改变的颜色
     * @param position   要改变的状态栏位置
     */
    public static void setWindowStatusBarColor(Activity activity, int colorResId, Position position) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                switch (position) {
                    case STATUS:
                        window.setStatusBarColor(ActivityCompat.getColor(activity, colorResId));
                        break;
                    case NAVIGATION:
                        window.setNavigationBarColor(ActivityCompat.getColor(activity, colorResId));
                        break;
                    case ALL:
                        window.setStatusBarColor(ActivityCompat.getColor(activity, colorResId));
                        window.setNavigationBarColor(ActivityCompat.getColor(activity, colorResId));
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
