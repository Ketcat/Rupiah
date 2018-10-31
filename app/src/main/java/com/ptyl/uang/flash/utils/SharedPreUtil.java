package com.ptyl.uang.flash.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by heise on 2018/5/31.
 */

public class SharedPreUtil {
    private static final String SHAREDNAME = "rupiah";
    public static final String PHONE = "phone";
    public static final String ISFIRST = "isfirst";
    public static final String ISGETINFO = "isgetinfo";
    public static final String PERSONAL = "personal";
    public static final String INDUSTRY = "industry";
    public static final String FAMILY = "family";
    public static final String OTHER = "other";
    public static final String TOKEN = "token";
    public static final String ISLOGIN = "islogin";
    public static final String LASTLOGINTIME = "lastlogintime";


    public static void saveBoolean(Context context, String key,boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putBoolean(key, value);
        ed.commit();
    }


    public static boolean readBoolean(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(key,false);
    }
    /**
     * 保存String型
     *
     * @param context 上下文
     * @param key     保存数据的key
     * @param value   要保存的数据
     */
    public static void saveSring(Context context, String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString(key, value);
        ed.commit();
    }

    /**
     * 读取本地保存的数据
     *
     * @param context 上下文
     * @param key     读取数据需要的KEY
     * @param def     读取失败时默认值
     * @return String
     */
    public static String readString(Context context, String key, String def) {
        SharedPreferences prefs = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        return prefs.getString(key, def);
    }


    public static void saveDrawable(Context context, String key, Bitmap bitmap) {
        SharedPreferences prefs = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
        String imageBase64 = new String(Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT));
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString(key, imageBase64);
        ed.commit();
    }

    public static Bitmap getDrawableByKey(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        String images = prefs.getString(key, "");
        byte[] bytes = images.getBytes();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    public static void saveLong(Context context, String key, long value) {
        SharedPreferences prefs = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putLong(key, value);
        ed.commit();
    }
    public static long readLong(Context context, String key, long def) {
        SharedPreferences prefs = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        return prefs.getLong(key, def);
    }

}
