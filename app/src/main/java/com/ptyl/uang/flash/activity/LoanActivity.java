package com.ptyl.uang.flash.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ptyl.uang.flash.MainActivity;
import com.ptyl.uang.flash.R;
import com.ptyl.uang.flash.adapter.DialogListAdapter;
import com.ptyl.uang.flash.bean.BankBean;
import com.ptyl.uang.flash.bean.LocationBean;
import com.ptyl.uang.flash.utils.AppInfoUtils;
import com.ptyl.uang.flash.utils.Contants;
import com.ptyl.uang.flash.utils.FileSizeUtil;
import com.ptyl.uang.flash.utils.LocationUtils;
import com.ptyl.uang.flash.utils.PhoneInfo;
import com.ptyl.uang.flash.utils.SharedPreUtil;
import com.ptyl.uang.flash.utils.StatusBarUtils;
import com.ptyl.uang.flash.view.LoadingCustom;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 贷款申请界面
 */
public class LoanActivity extends BasicActivity implements View.OnClickListener {
    Button btSubmit;
    ImageView ivBack;
    TextView tvTitle, tvMoneyNumber, tvDayTime, tvInterest, tvService, tvReal, tvMoney,
            tvBankName, tvBankCoupon, tvClause;
    RelativeLayout rlBankname, rlCoupon;
    EditText etBankCode, etReason;
    CheckBox loanCheckBox;
    Location gpsLocation;
    int type;
    int index;
    List<String> listName;
    String info, code, token, money, dates, interestAccr, remainAmount, serviceFeeAccr, imei,
            mobile, timestamp;
    List<BankBean> listBank;
    @SuppressLint("HandlerLeak")
    Handler handlerSkip = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadingCustom.dismissprogress();
            String result = msg.getData().getString("result");
            switch (msg.what) {
                case 1:
                    if (TextUtils.isEmpty(result)) {
                        upLocation();
                        Intent intent = new Intent(Contants.action_loan);
                        intent.putExtra("loanInfo", true);         //向广播接收器传递数据
                        sendBroadcast(intent);
                        MainActivity.mainActivity.setSelected(0);
                        //        Location gpsLocation = LocationUtils.getGPSLocation(MainActivity
                        // .this);
                        finish();
                    }
                    break;
                case 2:
                    if (!TextUtils.isEmpty(result)) {
                        try {
                            Gson gson = new Gson();
                            listBank = gson.fromJson(result, new TypeToken<List<BankBean>>() {
                            }.getType());
                            checkBankDialog(listBank);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected int getResourceId() {
        return R.layout.activity_loan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        StatusBarUtils.setWindowStatusBarColor(LoanActivity.this, R.color.tab_bg, StatusBarUtils
                .Position.STATUS);

    }

    @Override
    protected void initView() {
        btSubmit = findViewById(R.id.bt_loan_sure);
        tvMoneyNumber = findViewById(R.id.tv_money_number_loan);
        tvDayTime = findViewById(R.id.tv_day_time);
        tvTitle = findViewById(R.id.tv_title);
        tvMoney = findViewById(R.id.tv_money_loan);
        tvInterest = findViewById(R.id.tv_interest);
        tvService = findViewById(R.id.tv_service);
        tvReal = findViewById(R.id.tv_real);
        tvBankName = findViewById(R.id.tv_bank_name_value);
        rlBankname = findViewById(R.id.rl_bank_name);
        rlCoupon = findViewById(R.id.rl_coupon);
        tvBankCoupon = findViewById(R.id.tv_bank_coupon_value);
        tvClause = findViewById(R.id.tv_clause);
        ivBack = findViewById(R.id.iv_title_back);
        etBankCode = findViewById(R.id.et_bank_code);
        etReason = findViewById(R.id.et_loan_reason);
        loanCheckBox = findViewById(R.id.loan_check);
        ivBack.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type",1);
        info = getIntent().getStringExtra("info");
        money = getIntent().getStringExtra("money");
        dates = getIntent().getStringExtra("dates");
        gpsLocation = LocationUtils.getGPSLocation(LoanActivity.this);
        imei = PhoneInfo.getIMEI(LoanActivity.this);
        mobile = TextUtils.isEmpty(SharedPreUtil.readString(LoanActivity.this, SharedPreUtil
                .PHONE, "")) ? PhoneInfo.getNativePhoneNumber(LoanActivity.this) : SharedPreUtil
                .readString(LoanActivity.this, SharedPreUtil.PHONE, "");
        timestamp = System.currentTimeMillis() + "";
        if (type == 1) {
            tvMoneyNumber.setText(money + "");
            tvMoney.setText(money + "");
        } else {
            tvMoneyNumber.setText(FileSizeUtil.getStringNumber(money));
            tvMoney.setText(FileSizeUtil.getStringNumber(money));
        }
        tvDayTime.setText(dates.replace("D", "hari"));
        if (!TextUtils.isEmpty(info)) {
            try {
                Gson gson = new Gson();
                Map<String, String> map = gson.fromJson(info, Map.class);
                code = map.get("loanAppId");
                interestAccr = map.get("interestAccr");
                serviceFeeAccr = map.get("serviceFeeAccr");
                remainAmount = map.get("remainAmount");
                tvInterest.setText(getResources().getString(R.string.interest) + ":Rp " +
                        interestAccr);

                tvService.setText(getResources().getString(R.string.serviceTax) + ":Rp " +
                        serviceFeeAccr);
                tvReal.setText(getResources().getString(R.string.actualrrival) + ":Rp " +
                        remainAmount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        token = SharedPreUtil.readString(this, SharedPreUtil.TOKEN, "");
        listBank = new ArrayList<>();
        listName = new ArrayList<>();
        tvTitle.setText(R.string.loan_list_name);
    }

    private void upLocation() {
        if (gpsLocation != null) {
            LocationBean.DataBean dataBean = new LocationBean.DataBean();
            List<LocationBean.DataBean> listdate = new ArrayList<>();
            LocationBean locationBean = new LocationBean();
            dataBean.setLatitude(gpsLocation.getLatitude());
            dataBean.setLongitude(gpsLocation.getLongitude());
            dataBean.setCreateTime(System.currentTimeMillis());
            dataBean.setAltitude(0);
            listdate.add(dataBean);
            locationBean.setData(listdate);
            locationBean.setEarliestTime(0);
            locationBean.setLatestTime(System.currentTimeMillis());
            locationBean.setProtocolName("LOCATION");
            locationBean.setProtocolVersion(AppInfoUtils.packageCode(mContext) + "");
            locationBean.setTotalNumber(0);
            locationBean.setVersionName("");
            Gson gson = new Gson();
            upPhoneInfo(imei, mobile, timestamp, gson.toJson(locationBean));
        }
    }

    @Override
    protected void initListener() {
        btSubmit.setOnClickListener(this);
        rlBankname.setOnClickListener(this);
        rlCoupon.setOnClickListener(this);
        tvClause.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }


    public void loanapp(String bankCode, String cardNo, String applyFor, String applyChannel,
                        String applyPlatform, String couponId) {
        LoadingCustom.showprogress(this, "", true);
        OkHttpClient okHttpClient = new OkHttpClient();
////        Post请求
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("bankCode", bankCode);
        builder.add("cardNo", cardNo);
        builder.add("applyFor", applyFor);
        builder.add("applyChannel", applyChannel);
        builder.add("applyPlatform", applyPlatform);
        builder.add("couponId", couponId);
        RequestBody formBody = builder.build();
        Request request1 = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.LOANAPP + code + "/bank").put(formBody).build();
        Call call1 = okHttpClient.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handlerSkip.sendEmptyMessage(4);
                Log.e("LoanActivity", "ERROR1" + e.getMessage());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                Log.e("LoanActivity", "loanapp" + respones);
                handlerSkip.sendEmptyMessage(1);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.rl_bank_name:
                getBankInfo();
                break;
            //优惠券
            case R.id.rl_coupon:
                break;
            //借款合同
            case R.id.tv_clause:
                Intent loancontract = new Intent(LoanActivity.this, WebActivity.class);
                loancontract.putExtra("url", Contants.LOANCONTRACT);
                loancontract.putExtra("title", getResources().getString(R.string.loancontract));
                startActivity(loancontract);
                break;
            case R.id.bt_loan_sure:
                String bankName = tvBankName.getText().toString().trim();
                String reason = etReason.getText().toString().trim();
                String code = etBankCode.getText().toString().trim();
                if (!TextUtils.isEmpty(bankName) && !TextUtils.isEmpty(reason) && !TextUtils
                        .isEmpty(code)) {
                    if (loanCheckBox.isChecked()) {
                        loanapp(listBank.get(index).getBankCode(), code, reason, "NONE",
                                "ANDROID", "");
                    } else {
                        Toast.makeText(mContext, R.string.agree_condition, Toast.LENGTH_SHORT)
                                .show();
                    }
                } else {
                    Toast.makeText(mContext, R.string.complete_info, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void checkBankDialog(List<BankBean> listBank) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view_dialog = layoutInflater.inflate(R.layout.layout_list_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setContentView(view_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ListView listView = view_dialog.findViewById(R.id.dialog_list);
        if (listBank.size() > 0) {
            for (BankBean bankBean : listBank) {
                listName.add(bankBean.getBankName());
            }
        }
        DialogListAdapter adapter = new DialogListAdapter(listName, mContext);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                tvBankName.setText(listName.get(position));
                index = position;
            }
        });
    }

    public void getBankInfo() {
//        LoadingCustom.showprogress(this, "", true);
        OkHttpClient okHttpClient = new OkHttpClient();
//        get 请求
        Request request = new Request.Builder().url(Contants.GETBANKCODE).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handlerSkip.sendEmptyMessage(4);
                Log.e("LoanActivity", e.getMessage() + "失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", respones);
                message.setData(bundle);
                message.what = 2;
                handlerSkip.sendMessage(message);
                Log.e("LoanActivity", "getBankInfo" + respones);
            }
        });
    }

    private void upPhoneInfo(String imei, String mobile, String timestamp, String messageBody) {
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
                Log.e("Log", respones + "upPhoneInfo");
            }
        });
    }
}
