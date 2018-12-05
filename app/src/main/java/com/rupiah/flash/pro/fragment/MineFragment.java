package com.rupiah.flash.pro.fragment;

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
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rupiah.flash.pro.MainActivity;
import com.rupiah.flash.pro.R;
import com.rupiah.flash.pro.activity.LoanListActivity;
import com.rupiah.flash.pro.activity.OpinionActivity;
import com.rupiah.flash.pro.activity.WebActivity;
import com.rupiah.flash.pro.bean.IntegralBean;
import com.rupiah.flash.pro.utils.Contants;
import com.rupiah.flash.pro.utils.SharedPreUtil;
import com.rupiah.flash.pro.view.LoadingCustom;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 我的页面
 */
public class MineFragment extends BasicFragment implements View.OnClickListener {
    RelativeLayout rlCoupons, rlFriends, rlMineLoan, rlHelpCenter, rlAboutUs, rlPrivacy,
            rlContactUs, rlOnline;
    Button btExit;
    TextView tvCouponsNumber, tvFriendsNumber, tvMineNumber, tvIntegralValue;
    private static final int CONTACT_US = 1;
    private static final int EXIT = 2;
    String phone, token;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.getData().getString("result");
            switch (msg.what) {
                case 1:
                    if (TextUtils.isEmpty(result)) {
                        logOutResult();
                    } else {
                        Toast.makeText(mContext, R.string.logout_fail, Toast.LENGTH_SHORT).show();
                    }
                    LoadingCustom.dismissprogress();
                    break;
                case 5:
                    checkIntegral(result);
                    break;
                case 0:
                    LoadingCustom.dismissprogress();
                    break;
            }
        }
    };

    private void checkIntegral(String result) {
        if (!TextUtils.isEmpty(result)) {
            try {
                Gson gson = new Gson();
                IntegralBean integralBean = gson.fromJson(result, IntegralBean.class);
                if (TextUtils.isEmpty(integralBean.getError())) {
                    String integral = integralBean.getData();
                    String value = integral.substring(15, integral.length() - 1);
                    tvIntegralValue.setText(value);
                } else if ("err.auth.access.denied".equals(integralBean.getError())) {
                    logOutResult();
                } else {
                    Toast.makeText(mContext, integralBean.getError(), Toast
                            .LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LoadingCustom.dismissprogress();
    }

    private void logOutResult() {
        SharedPreUtil.saveSring(mContext, SharedPreUtil.PHONE, "");
        SharedPreUtil.saveSring(mContext, SharedPreUtil.TOKEN, "");
        SharedPreUtil.saveBoolean(mContext, SharedPreUtil.ISLOGIN, false);
        phone = "";
        token = "";
        isLogin = false;
        MainActivity.isFirst = true;
        tvMineNumber.setText("");
        btExit.setText(R.string.mine_login);
        Intent intent = new Intent(Contants.action);
        intent.putExtra("isShow", false);
        mContext.sendBroadcast(intent);
    }

    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(Contants.action_mine);
        getActivity().registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
//        rlCoupons = view.findViewById(R.id.rl_coupons);
//        rlFriends = view.findViewById(R.id.rl_friends);
        rlMineLoan = view.findViewById(R.id.rl_mine_loan);
        rlHelpCenter = view.findViewById(R.id.rl_help_center);
        rlAboutUs = view.findViewById(R.id.rl_about_us);
        rlPrivacy = view.findViewById(R.id.rl_privacy);
        rlContactUs = view.findViewById(R.id.rl_contact_us);
        rlOnline = view.findViewById(R.id.rl_online);
        tvIntegralValue = view.findViewById(R.id.integral_value);
//        tvCouponsNumber = view.findViewById(R.id.tv_mine_coupons_number);
//        tvFriendsNumber = view.findViewById(R.id.tv_friends_number);
        tvMineNumber = view.findViewById(R.id.mine_user_number);
        btExit = view.findViewById(R.id.mine_exit);
        phone = SharedPreUtil.readString(getContext(), SharedPreUtil.PHONE, "");
        token = SharedPreUtil.readString(getContext(), SharedPreUtil.TOKEN, "");
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(token)) {
            tvMineNumber.setText(phone);
            btExit.setText(R.string.mine_exit);
        } else {
            tvMineNumber.setText("");
            btExit.setText(R.string.mine_login);
        }
        initListener();
        getIntegral();//获取积分
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initListener() {
//        rlCoupons.setOnClickListener(this);
//        rlFriends.setOnClickListener(this);
        rlMineLoan.setOnClickListener(this);
        rlHelpCenter.setOnClickListener(this);
        rlAboutUs.setOnClickListener(this);
        rlPrivacy.setOnClickListener(this);
        rlContactUs.setOnClickListener(this);
        rlOnline.setOnClickListener(this);
        btExit.setOnClickListener(this);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            phone = SharedPreUtil.readString(getContext(), SharedPreUtil.PHONE, "");
            token = SharedPreUtil.readString(getContext(), SharedPreUtil.TOKEN, "");
            if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(token)) {
                tvMineNumber.setText(phone);
                btExit.setText(R.string.mine_exit);
            } else {
                tvMineNumber.setText("");
                btExit.setText(R.string.mine_login);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //优惠券
//            case R.id.rl_coupons:
//                break;
//            //邀请好友
//            case R.id.rl_friends:
//                break;
            //我的贷款
            case R.id.rl_mine_loan:
                if (isLogin) {
                    startActivity(new Intent(getActivity(), LoanListActivity.class));
                } else {
                    GoToMainActivity();
                }
                break;
            //帮助中心
            case R.id.rl_help_center:
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", Contants.HELPPAGE);
                intent.putExtra("title", getResources().getString(R.string.help_center));
                startActivity(intent);
                break;
            //关于我们
            case R.id.rl_about_us:
//                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                Intent intentUs = new Intent(getActivity(), WebActivity.class);
                intentUs.putExtra("url", Contants.ABOUTUS);
                intentUs.putExtra("title", getResources().getString(R.string.about_us));
                startActivity(intentUs);
                break;
            //隐私政策
            case R.id.rl_privacy:
//                startActivity(new Intent(getActivity(), PrivacyActivity.class));
                Intent privacy = new Intent(getActivity(), WebActivity.class);
                privacy.putExtra("url", Contants.PRIVACYPOLICY);
                privacy.putExtra("title", getResources().getString(R.string.privacy));
                startActivity(privacy);
                break;
            //联系我们
            case R.id.rl_contact_us:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission
                        .CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    MineFragment.this.requestPermissions(new String[]{Manifest.permission
                            .CALL_PHONE}, 1);
                } else {
                    callPhoneDialog();
                }
                break;
            //意见回馈
            case R.id.rl_online:
                startActivity(new Intent(getActivity(), OpinionActivity.class));
                break;
            //退出
            case R.id.mine_exit:
                isLogin = SharedPreUtil.readBoolean(mContext, SharedPreUtil.ISLOGIN);
                if (isLogin) {
                    exitDialog();
                } else {
                    GoToMainActivity();
                }
                break;
            default:
                break;
        }
    }

    private void exitDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view_dialog = layoutInflater.inflate(R.layout.layout_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.show();
        dialog.getWindow().setContentView(view_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView dialogTitle = (TextView) view_dialog.findViewById(R.id.dialog_title);
        TextView dialogDetails = (TextView) view_dialog.findViewById(R.id.dialog_details);
        TextView dialogCancel = (TextView) view_dialog.findViewById(R.id.dialog_cancel);
        TextView dialogSure = (TextView) view_dialog.findViewById(R.id.dialog_sure);
        dialogTitle.setText(R.string.sign_out);
        dialogDetails.setText(R.string.are_you_sure);
        dialogCancel.setText(R.string.cancel);
        dialogSure.setText(R.string.sure);
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                logOut();
            }
        });
    }

    private void callPhoneDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view_dialog = layoutInflater.inflate(R.layout.layout_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.show();
        dialog.getWindow().setContentView(view_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView dialogDetails = (TextView) view_dialog.findViewById(R.id.dialog_details);
        TextView dialogCancel = (TextView) view_dialog.findViewById(R.id.dialog_cancel);
        TextView dialogSure = (TextView) view_dialog.findViewById(R.id.dialog_sure);
        dialogDetails.setText(R.string.call_phone_number);
        dialogCancel.setText(R.string.cancel);
        dialogSure.setText(R.string.sure);
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                callPhone(Contants.phoneNum);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneDialog();
            } else {
                Toast.makeText(getActivity(), R.string.you_mast_open_call_permission, Toast
                        .LENGTH_SHORT).show();
            }
        }
    }

    private void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    public void logOut() {
        LoadingCustom.showprogress(getActivity(), "", true);
        OkHttpClient okHttpClient = new OkHttpClient();
        final FormBody.Builder builder = new FormBody.Builder();
        builder.add(Contants.TOKEN, token);
        RequestBody formBody = builder.build();
        Request request = new Request.Builder().post(formBody).url(Contants.LOGOUT).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(0);
                Log.e("MineFragment", e.getMessage() + "失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", respones);
                message.setData(bundle);
                message.what = 1;
                handler.sendMessage(message);
                Log.e("MineFragment", respones);
            }
        });
    }

    public void getIntegral() {
        OkHttpClient okHttpClient = new OkHttpClient();
//        get 请求
        Request request = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.INTERGRAL).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(0);
                Log.e("MineFragment", e.getMessage() + "失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", respones);
                message.setData(bundle);
                message.what = 5;
                handler.sendMessage(message);
                Log.e("MineFragment", respones);
            }
        });
    }

}
