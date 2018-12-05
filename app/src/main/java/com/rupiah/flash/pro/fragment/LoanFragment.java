package com.rupiah.flash.pro.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rupiah.flash.pro.MainActivity;
import com.rupiah.flash.pro.R;
import com.rupiah.flash.pro.activity.LoanActivity;
import com.rupiah.flash.pro.activity.WebActivity;
import com.rupiah.flash.pro.bean.CanLoanBean;
import com.rupiah.flash.pro.bean.LatestBean;
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
 * 贷款页面
 */
public class LoanFragment extends BasicFragment implements View.OnClickListener {

    TextView tvTitle, tvMoneyNumber6, tvMoneyNumber10, tvMyMoneyNumber, tvDatas7, tvDatas14,
            tvDatas21, tvType, tvOrderNumber, tvIdCode, tvSubmitInfoTime, tvLoanNumber,
            tvLoanTime, tvCostNumber, tvRePayNumber, tvBankName, tvBankCode, tvExamineTime,
            tvSubmitTime;
    Button btSureLoan;
    RelativeLayout rlMoneyNumber6, rlMoneyNumber10, rlDates7, rlDates14;
    LinearLayout linearLayout, llResult, llName, llValue;
    ImageView ivLoanInfo, ivValue6, ivValue10, ivValue7, ivValue14;
    String money;
    String dates;
    String token;
    boolean isCheck = false;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.getData().getString("result");
            switch (msg.what) {
                case 1:
                    checkDate(result);
                    break;
                case 5:
                    checkCanLoan(result);
                    break;
                case 3:
                    checkCanLoan1(result);
                    break;
                case 0:
                    LoadingCustom.dismissprogress();
                    break;
            }
        }
    };

    private void checkDate(String result) {
        if (!TextUtils.isEmpty(result)) {
            try {
                Gson gson = new Gson();
                LatestBean latestBean = gson.fromJson(result, LatestBean.class);
                //添加数据
                if (latestBean != null) {
                    tvOrderNumber.setText(latestBean.getLoanAppId() + "");
                    tvIdCode.setText(latestBean.getCredentialNo());
                    tvSubmitInfoTime.setText(latestBean.getCreateTime());
                    tvLoanNumber.setText(latestBean.getAmount() + "");
                    tvLoanTime.setText(latestBean.getPeriod() + (latestBean
                            .getPeriodUnit().equals("D") ? "hair" : latestBean
                            .getPeriodUnit().equals("W") ? "minggu" : latestBean
                            .getPeriodUnit().equals("M") ? "bulan" : "tahun"));
                    tvCostNumber.setText(latestBean.getCost() + "");
                    tvRePayNumber.setText(latestBean.getTotalAmount() + "");
                    tvBankName.setText(latestBean.getBankCode());
                    tvBankCode.setText(latestBean.getCardNo());
                    tvExamineTime.setText(latestBean.getStatusLogs().get(0)
                            .getCreateTime());
                    tvSubmitTime.setText(latestBean.getCreateTime());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LoadingCustom.dismissprogress();
    }

    private void checkCanLoan(String result) {
        if (!TextUtils.isEmpty(result)) {
            try {
                Gson gson = new Gson();
                CanLoanBean errorBean = gson.fromJson(result, CanLoanBean.class);
                if (!TextUtils.isEmpty(errorBean.getSuccessful_loan_times() + "")) {
                    checkPerson(errorBean.getSuccessful_loan_times());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void checkCanLoan1(String result) {
        if (!TextUtils.isEmpty(result)) {
            try {
                Gson gson = new Gson();
                CanLoanBean errorBean = gson.fromJson(result, CanLoanBean.class);
                if (!TextUtils.isEmpty(errorBean.getError())) {
                    Toast.makeText(mContext, errorBean.getMessage(), Toast.LENGTH_SHORT).show();
                } else if (errorBean.isLoan_submitted()) {
                    skipDialog(errorBean);
                } else if (errorBean.isLoan_qualification()) {
                    Intent intent = new Intent(Contants.action);
                    intent.putExtra("money", money);
                    intent.putExtra("date", dates);
                    intent.putExtra("isShow", true);
                    getActivity().sendBroadcast(intent);
                    MainActivity.mainActivity.setSelected(1);
                }else{
                    Toast.makeText(mContext, errorBean.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LoadingCustom.dismissprogress();
    }

    private void isSkipLoan(CanLoanBean errorBean) {
        Intent intent = new Intent(getContext(), LoanActivity.class);
        intent.putExtra("type", 0);
        intent.putExtra("info", errorBean.getInfo());
        intent.putExtra("money", errorBean.getAmount() + "");
        intent.putExtra("dates", errorBean.getPeriod());
        startActivity(intent);
    }

    public LoanFragment() {
        // Required empty public constructor
    }


    public static LoanFragment newInstance() {
        LoanFragment fragment = new LoanFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_loan, container, false);
        // Inflate the layout for this fragment
        initView(view);
        qualification();
        return view;
    }

    private void initDate() {
        tvTitle.setText(R.string.loan_title);
        token = SharedPreUtil.readString(getActivity(), SharedPreUtil.TOKEN, "");
        money = tvMoneyNumber6.getText().toString().trim();
        dates = tvDatas7.getText().toString().trim();
        isCheck = false;
        checkImageView();
    }

    private void initView(View view) {
        tvTitle = view.findViewById(R.id.tv_title);
        tvMoneyNumber6 = view.findViewById(R.id.tv_loan_money6);
        tvMoneyNumber10 = view.findViewById(R.id.tv_loan_money10);
        tvDatas7 = view.findViewById(R.id.tv_loan_data7);
        tvDatas14 = view.findViewById(R.id.tv_loan_data14);
//        tvDatas21 = view.findViewById(R.id.tv_loan_data21);
        tvMyMoneyNumber = view.findViewById(R.id.tv_loan_number);
        tvType = view.findViewById(R.id.tv_loan_type);
        btSureLoan = view.findViewById(R.id.bt_loan);
        linearLayout = view.findViewById(R.id.ll_loan);
        llResult = view.findViewById(R.id.ll_result);
        tvOrderNumber = view.findViewById(R.id.tv_order_number);
        tvIdCode = view.findViewById(R.id.tv_id_code);
        tvSubmitInfoTime = view.findViewById(R.id.tv_submit_info_time);
        tvLoanNumber = view.findViewById(R.id.tv_loan_money_number);
        tvLoanTime = view.findViewById(R.id.tv_loan_time);
        tvCostNumber = view.findViewById(R.id.tv_cost_number);
        tvRePayNumber = view.findViewById(R.id.tv_repay_number);
        tvBankName = view.findViewById(R.id.tv_bank_name);
        tvBankCode = view.findViewById(R.id.tv_bank_code);
        tvExamineTime = view.findViewById(R.id.tv_examine_time);
        tvSubmitTime = view.findViewById(R.id.tv_submit_time);
        llName = view.findViewById(R.id.ll_name_visible_gone);
        llValue = view.findViewById(R.id.ll_value_visible_gone);
        ivLoanInfo = view.findViewById(R.id.iv_loan_info);
        ivValue6 = view.findViewById(R.id.iv_value6);
        ivValue10 = view.findViewById(R.id.iv_value10);
        ivValue7 = view.findViewById(R.id.iv_value7);
        ivValue14 = view.findViewById(R.id.iv_value14);
        rlMoneyNumber6 = view.findViewById(R.id.rl_money_number6);
        rlMoneyNumber10 = view.findViewById(R.id.rl_money_number10);
        rlDates7 = view.findViewById(R.id.rl_dates7);
        rlDates14 = view.findViewById(R.id.rl_dates14);
//        rlDates21= view.findViewById(R.id.rl_dates21);
        rlMoneyNumber6.setOnClickListener(this);
        rlMoneyNumber10.setOnClickListener(this);
        rlDates7.setOnClickListener(this);
        rlDates14.setOnClickListener(this);
//        rlDates21.setOnClickListener(this);
        btSureLoan.setOnClickListener(this);
        tvType.setOnClickListener(this);
        ivLoanInfo.setOnClickListener(this);
        linearLayout.setVisibility(View.VISIBLE);
        llResult.setVisibility(View.GONE);
        rlMoneyNumber6.setBackgroundResource(R.drawable.right_button);
        tvMoneyNumber6.setTextColor(getResources().getColor(R.color.green));
        ivValue6.setVisibility(View.VISIBLE);
        rlDates7.setBackgroundResource(R.drawable.right_button);
        tvDatas7.setTextColor(getResources().getColor(R.color.green));
        ivValue7.setVisibility(View.VISIBLE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Contants.action_loan);
        getActivity().registerReceiver(myReceiver, intentFilter);
        initDate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_loan:
                token = SharedPreUtil.readString(getActivity(), SharedPreUtil.TOKEN, "");
                isLogin = SharedPreUtil.readBoolean(mContext, SharedPreUtil.ISLOGIN);
                if (!TextUtils.isEmpty(token)) {
                    qualification1();
                } else {
                    GoToMainActivity();
                }
                break;
            case R.id.tv_loan_type:
//                startActivity(new Intent(getActivity(), LoanChannelActivity.class));
                Intent chanel = new Intent(getActivity(), WebActivity.class);
                chanel.putExtra("url", Contants.REPAYMENT);
                chanel.putExtra("title", getResources().getString(R.string.chanel));
                startActivity(chanel);
                break;
            case R.id.iv_loan_info:
                checkImageView();
                isCheck = !isCheck;
                break;
            case R.id.rl_money_number6:
                rlMoneyNumber10.setBackgroundResource(R.drawable.money_bg);
                rlMoneyNumber6.setBackgroundResource(R.drawable.right_button);
                money = tvMoneyNumber6.getText().toString().trim();
                tvMoneyNumber6.setTextColor(getResources().getColor(R.color.green));
                tvMoneyNumber10.setTextColor(getResources().getColor(R.color.hint_color));
                tvMyMoneyNumber.setText(money);
                ivValue6.setVisibility(View.VISIBLE);
                ivValue10.setVisibility(View.GONE);
                break;
            case R.id.rl_money_number10:
                rlMoneyNumber6.setBackgroundResource(R.drawable.money_bg);
                rlMoneyNumber10.setBackgroundResource(R.drawable.right_button);
                money = tvMoneyNumber10.getText().toString().trim();
                tvMoneyNumber10.setTextColor(getResources().getColor(R.color.green));
                tvMoneyNumber6.setTextColor(getResources().getColor(R.color.hint_color));
                tvMyMoneyNumber.setText(money);
                ivValue6.setVisibility(View.GONE);
                ivValue10.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_dates7:
                dates = tvDatas7.getText().toString().trim();
                rlDates7.setBackgroundResource(R.drawable.right_button);
                rlDates14.setBackgroundResource(R.drawable.money_bg);
//                rlDates21.setBackgroundResource(R.drawable.money_bg);
                tvDatas7.setTextColor(getResources().getColor(R.color.green));
//                tvDatas21.setTextColor(getResources().getColor(R.color.hint_color));
                tvDatas14.setTextColor(getResources().getColor(R.color.hint_color));
                ivValue7.setVisibility(View.VISIBLE);
                ivValue14.setVisibility(View.GONE);
                break;
            case R.id.rl_dates14:
                rlDates7.setBackgroundResource(R.drawable.money_bg);
//                rlDates21.setBackgroundResource(R.drawable.money_bg);
                rlDates14.setBackgroundResource(R.drawable.right_button);
                dates = tvDatas14.getText().toString().trim();
                tvDatas7.setTextColor(getResources().getColor(R.color.hint_color));
//                tvDatas21.setTextColor(getResources().getColor(R.color.hint_color));
                tvDatas14.setTextColor(getResources().getColor(R.color.green));
                ivValue7.setVisibility(View.GONE);
                ivValue14.setVisibility(View.VISIBLE);
                break;
//            case R.id.rl_dates21:
//                rlDates7.setBackgroundResource(R.drawable.money_bg);
//                rlDates14.setBackgroundResource(R.drawable.money_bg);
//                rlDates21.setBackgroundResource(R.drawable.loan_bg);
//                dates =  tvDatas21.getText().toString().trim();
//                tvDatas7.setTextColor(getResources().getColor(R.color.hint_color));
//                tvDatas14.setTextColor(getResources().getColor(R.color.hint_color));
//                tvDatas21.setTextColor(getResources().getColor(R.color.green));
//                break;
            default:
                break;
        }
    }

    private void checkImageView() {
        if (!isCheck) {
            ivLoanInfo.setImageResource(R.mipmap.check_down);
            llName.setVisibility(View.GONE);
            llValue.setVisibility(View.GONE);
        } else {
            ivLoanInfo.setImageResource(R.mipmap.check_up);
            llName.setVisibility(View.VISIBLE);
            llValue.setVisibility(View.VISIBLE);
        }
    }

    private void checkPerson(int count) {
        if (count <= 1) {
            rlMoneyNumber6.setVisibility(View.VISIBLE);
            rlDates7.setVisibility(View.VISIBLE);
            rlMoneyNumber10.setVisibility(View.GONE);
            rlDates14.setVisibility(View.GONE);
        } else if (count == 2) {
            rlMoneyNumber6.setVisibility(View.VISIBLE);
            rlDates7.setVisibility(View.VISIBLE);
            rlMoneyNumber10.setVisibility(View.VISIBLE);
            rlDates14.setVisibility(View.GONE);
        } else if (count >= 3) {
            rlMoneyNumber6.setVisibility(View.VISIBLE);
            rlDates7.setVisibility(View.VISIBLE);
            rlMoneyNumber10.setVisibility(View.VISIBLE);
            rlDates14.setVisibility(View.VISIBLE);
        }
    }

    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra("loanInfo", false)) {
                getLatest();
                linearLayout.setVisibility(View.GONE);
                llResult.setVisibility(View.VISIBLE);
            } else {
                linearLayout.setVisibility(View.VISIBLE);
                llResult.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(myReceiver);
    }

    public void getLatest() {
        LoadingCustom.showprogress(getContext(), "", true);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.LATEST).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(1);
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
                Log.e("Log", respones + "getlast");
            }
        });
    }

    /**
     * 贷款申请资格
     */
    public void qualification1() {
        LoadingCustom.showprogress(getActivity(), "", false);
        OkHttpClient okHttpClient = new OkHttpClient();
//        get 请求
        Request request = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.QUALIFICATION).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(0);
                Log.e("LoanFragment", e.getMessage() + "失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", respones);
                message.setData(bundle);
                message.what = 3;
                handler.sendMessage(message);
                Log.e("LoanFragment", respones + "qualification()");
            }
        });
    }

    /**
     * 贷款申请资格
     */
    public void qualification() {
//        LoadingCustom.showprogress(getActivity(), "", false);
        OkHttpClient okHttpClient = new OkHttpClient();
//        get 请求
        Request request = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.QUALIFICATION).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(0);
                Log.e("LoanFragment", e.getMessage() + "失败");
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
                Log.e("LoanFragment", respones + "qualification()");
            }
        });
    }

    private void skipDialog(final CanLoanBean errorBean) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view_dialog = layoutInflater.inflate(R.layout.layout_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.show();
        dialog.getWindow().setContentView(view_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView dialogTitle = (TextView) view_dialog.findViewById(R.id.dialog_title);
        TextView dialogDetails = (TextView) view_dialog.findViewById(R.id.dialog_details);
        TextView dialogCancel = (TextView) view_dialog.findViewById(R.id.dialog_cancel);
        TextView dialogSure = (TextView) view_dialog.findViewById(R.id.dialog_sure);
        dialogTitle.setText(R.string.skip_title);
        dialogDetails.setText(R.string.skip_details);
        dialogCancel.setText(R.string.cancel);
        dialogSure.setText(R.string.update);
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                cancel(errorBean);
            }
        });
        dialogSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                isSkipLoan(errorBean);
            }
        });
    }

    public void cancel(CanLoanBean canLoanBean) {
        LoadingCustom.showprogress(getActivity(), "", true);
        OkHttpClient okHttpClient = new OkHttpClient();
//     ////        Post请求
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("loanAppId", canLoanBean.getLoan_app_id() + "");
        RequestBody formBody = builder.build();
        Request request1 = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.CANCEL).post(formBody).build();
        Call call1 = okHttpClient.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                handler.sendEmptyMessage(0);
                Log.e("LoanFragment", respones + "cancel");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
