package com.rupiah.flash.pro.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rupiah.flash.pro.R;
import com.rupiah.flash.pro.adapter.PopListAdapter;
import com.rupiah.flash.pro.bean.LoanListBean;
import com.rupiah.flash.pro.bean.PayLoanBean;
import com.rupiah.flash.pro.utils.Contants;
import com.rupiah.flash.pro.utils.SharedPreUtil;
import com.rupiah.flash.pro.utils.StatusBarUtils;
import com.rupiah.flash.pro.view.LoadingCustom;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/***
 * 申请还款
 * */
public class PayLoanActivity extends BasicActivity implements View.OnClickListener {
    TextView tvTitle, tvPayId, tvPayMoney, tvPayTime, tvPayDate,tvPayingNumber, payId, payStatus, payMoney,
            payMata, payInfoType, payPhone, payNumber, payCode, bankType, payType;
    ImageView ivBack;
    RelativeLayout rlPayType, rlBankType;
    Button btPaySubmit;
    LinearLayout llPay, llPayInfo, llPayLoan;
    PopListAdapter adapter;
    List<String> list;
    EditText etPayMoney, etPayPhone;
    private LoanListBean loanListBean;
    private String money;
    private String phone;
    private String type;
    private String bankName, token;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadingCustom.dismissprogress();
            String result = msg.getData().getString("result");
            if (!TextUtils.isEmpty(result)) {
                Gson gson = new Gson();
                PayLoanBean payLoanBean = gson.fromJson(result, PayLoanBean.class);
                if (payLoanBean != null) {
                    llPay.setVisibility(View.GONE);
                    llPayInfo.setVisibility(View.VISIBLE);
                    payId.setText(payLoanBean.getDepositId() + "");
                    payStatus.setText(payLoanBean.getStatus() + "");
                    payMoney.setText(payLoanBean.getPrice() + "");
                    payMata.setText(payLoanBean.getCurrency());
                    payInfoType.setText(payLoanBean.getPayType());
                    payPhone.setText(payLoanBean.getMsisidn() + "");
                    payNumber.setText(payLoanBean.getBankType() + "");
                    payCode.setText(payLoanBean.getPaymentCode());
                } else {
                    llPay.setVisibility(View.VISIBLE);
                    llPayInfo.setVisibility(View.GONE);
                }
            } else {
                llPay.setVisibility(View.VISIBLE);
                llPayInfo.setVisibility(View.GONE);
            }
        }
    };


    @Override
    protected int getResourceId() {
        return R.layout.activity_pay_loan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        StatusBarUtils.setWindowStatusBarColor(PayLoanActivity.this, R.color.tab_bg,
                StatusBarUtils.Position.STATUS);
    }

    @Override
    protected void initView() {
        tvTitle = findViewById(R.id.tv_title);
        tvPayId = findViewById(R.id.tv_pay_order_code);
        tvPayMoney = findViewById(R.id.tv_loan_money_pay);
        tvPayTime = findViewById(R.id.tv_loan_time_pay);
        tvPayDate = findViewById(R.id.tv_pay_date);
        tvPayingNumber = findViewById(R.id.tv_paying_number);
        ivBack = findViewById(R.id.iv_title_back);
        ivBack.setVisibility(View.VISIBLE);
        rlBankType = findViewById(R.id.rl_pay_bank);
        rlPayType = findViewById(R.id.rl_pay_type);
        btPaySubmit = findViewById(R.id.bt_pay);
        llPay = findViewById(R.id.ll_pay);
        llPayInfo = findViewById(R.id.ll_pay_info);
        llPayLoan = findViewById(R.id.ll_pay_loan);
        payId = findViewById(R.id.pay_info_id);
        payStatus = findViewById(R.id.pay_info_status);
        payMoney = findViewById(R.id.pay_info_money);
        payMata = findViewById(R.id.pay_info_mata);
        bankType = findViewById(R.id.bank_type);
        payType = findViewById(R.id.pay_type);
        payInfoType = findViewById(R.id.pay_info_type);
        payPhone = findViewById(R.id.pay_info_phone);
        payNumber = findViewById(R.id.pay_info_number);
        payCode = findViewById(R.id.pay_info_bank_code);
        etPayMoney = findViewById(R.id.et_pay_money);
        etPayPhone = findViewById(R.id.et_pay_phone);
        llPay.setVisibility(View.VISIBLE);
        llPayInfo.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.pem);
        token = SharedPreUtil.readString(this, SharedPreUtil.TOKEN, "");
        Bundle bundle = getIntent().getBundleExtra("bundle");
        loanListBean = (LoanListBean) bundle.getSerializable("bean");
        if (loanListBean != null) {
            tvPayId.setText(loanListBean.getLoanAppId() + "");
            tvPayTime.setText(loanListBean.getPeriod() + (loanListBean.getPeriodUnit().equals
                    ("D") ? "hari" : loanListBean.getPeriodUnit().equals("W") ? "minggu" :
                    loanListBean.getPeriodUnit().equals("M") ? "bulan" : "tahun"));
            tvPayDate.setText(loanListBean.getCreateTime());
            tvPayMoney.setText(loanListBean.getAmount() + "");
            tvPayingNumber.setText(loanListBean.getRemainAmount()+"");
        }
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        btPaySubmit.setOnClickListener(this);
        rlPayType.setOnClickListener(this);
        rlBankType.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_pay_type:
                createPopwindow(rlPayType, payType, "ATM", "OTC");
                break;
            case R.id.rl_pay_bank:
                createPopwindow(rlBankType, bankType, "PERMATA", "BNI");
                break;
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.bt_pay:
                money = etPayMoney.getText().toString().trim();
                phone = etPayPhone.getText().toString().trim();
                bankName = bankType.getText().toString().trim();
                type = payType.getText().toString().trim();
                if ("OTC".equals(type)) {
                    if (!TextUtils.isEmpty(money) && !TextUtils.isEmpty(type)) {

                        payPost(loanListBean.getLoanAppId() + "", money, type, phone, bankName);
                    }
                } else {
                    if (!TextUtils.isEmpty(money) && !TextUtils.isEmpty(phone) && !TextUtils
                            .isEmpty(bankName) && !TextUtils.isEmpty(type)) {

                        payPost(loanListBean.getLoanAppId() + "", money, type, phone, bankName);
                    }
                }
                break;
            default:
                break;
        }
    }

    public void createPopwindow(final RelativeLayout v, final TextView textView, String... strs) {
        list = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            list.add(strs[i]);
        }
        View view = View.inflate(mContext, R.layout.layout_popupwindow, null);
        final ListView listView = view.findViewById(R.id.pop_list);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams
                .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        listView.setAdapter(new PopListAdapter(list, mContext));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(list.get(position));
                if (v.getId() == R.id.rl_pay_type) {
                    if ("OTC".equals(list.get(position))) {
                        llPayLoan.setVisibility(View.GONE);
                    } else {
                        llPayLoan.setVisibility(View.VISIBLE);
                    }
                }
                popupWindow.dismiss();
            }
        });
        popupWindow.setTouchable(true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable();
        popupWindow.setBackgroundDrawable(bitmapDrawable);
        popupWindow.showAsDropDown(v);
    }

    public void payPost(String loanAppId, String amount, String payType, String msisidn, String
            bankType) {
        LoadingCustom.showprogress(this, "", true);
        OkHttpClient okHttpClient = new OkHttpClient();
        ////        Post请求
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("loanAppId", loanAppId);
        builder.add("amount", amount);
        builder.add("payType", payType);
        builder.add("msisdn", msisidn);
        builder.add("bankType", bankType);
        RequestBody formBody = builder.build();
        Request request1 = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.DEPOSIT).post(formBody).build();

        Call call1 = okHttpClient.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(1);
                Log.e("Log", "ERROR1" + e.getMessage());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", respones);
                message.setData(bundle);
                handler.sendMessage(message);
                Log.e("Log", respones + "Post");
            }
        });
    }
}
