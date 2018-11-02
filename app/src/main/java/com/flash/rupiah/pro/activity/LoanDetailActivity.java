package com.flash.rupiah.pro.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.flash.rupiah.pro.R;
import com.flash.rupiah.pro.adapter.DetailListAdapter;
import com.flash.rupiah.pro.adapter.PayListAdapter;
import com.flash.rupiah.pro.bean.LoanListBean;
import com.flash.rupiah.pro.bean.PayLoanListBean;
import com.flash.rupiah.pro.utils.Contants;
import com.flash.rupiah.pro.utils.FileSizeUtil;
import com.flash.rupiah.pro.utils.SharedPreUtil;
import com.flash.rupiah.pro.utils.StatusBarUtils;
import com.flash.rupiah.pro.view.AuditProgressView;
import com.flash.rupiah.pro.view.LoadingCustom;
import com.flash.rupiah.pro.view.MyListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

public class LoanDetailActivity extends BasicActivity implements View.OnClickListener {
    String[] names;
    LoanListBean loanListBean;
    LinearLayout linearLayout;
    TextView tvTitle, tvView1, tvView2, view1, view2, tvWeb, tvDayTime, tvMoneyNumber;
    Button detailSubmit;
    ImageView ivBack;
    List<LoanListBean.StatusLogsBean> statusLogs;
    DetailListAdapter detailListAdapter;
    MyListView listView;
    List<String> listValue;
    ListView payListView;
    RelativeLayout rlLoanDetail, rlListLoan, rlNoLoanInfo;
    ScrollView scrollView;
    String token;
    List<PayLoanListBean> payList;
    PayListAdapter adapter;
    RelativeLayout rlNoInfo;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadingCustom.dismissprogress();
            String result = msg.getData().getString("result");
            switch (msg.what) {
                case 4:
                    detailSubmit.setText("Telah dibatalkan");
                    detailSubmit.setBackgroundResource(R.drawable.login_next_style);
                    detailSubmit.setEnabled(false);
                    break;
                case 1:
                    if (!TextUtils.isEmpty(result)) {
                        try {
                            Gson gson = new Gson();
                            payList = gson.fromJson(result, new TypeToken<List<PayLoanListBean>>() {
                            }.getType());
                            if (payList.size() > 0) {
                                adapter = new PayListAdapter(LoanDetailActivity.this, payList);
                                payListView.setAdapter(adapter);
                                rlNoLoanInfo.setVisibility(View.GONE);
                                payListView.setVisibility(View.VISIBLE);
                            } else {
                                rlNoLoanInfo.setVisibility(View.VISIBLE);
                                payListView.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            rlNoLoanInfo.setVisibility(View.VISIBLE);
                            payListView.setVisibility(View.GONE);
                        }
                    } else {
                        rlNoLoanInfo.setVisibility(View.VISIBLE);
                        payListView.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    @Override
    protected int getResourceId() {
        return R.layout.activity_loan_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        StatusBarUtils.setWindowStatusBarColor(LoanDetailActivity.this, R.color.tab_bg,
                StatusBarUtils.Position.STATUS);
    }

    @Override
    protected void initView() {
        listView = findViewById(R.id.detail_list);
        linearLayout = findViewById(R.id.ll_audit_content);
        tvTitle = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_title_back);
        tvView1 = findViewById(R.id.tv_view1);
        tvDayTime = findViewById(R.id.tv_day_time);
        tvMoneyNumber = findViewById(R.id.tv_money_number_loan);
        view1 = findViewById(R.id.view1);
        tvView1.setTextColor(getResources().getColor(R.color.green));
        tvView2 = findViewById(R.id.tv_view2);
        view2 = findViewById(R.id.view2);
        scrollView = findViewById(R.id.scrollView);
        payListView = findViewById(R.id.pay_list);
        rlNoLoanInfo = findViewById(R.id.rl_no_loan_info);
        rlLoanDetail = findViewById(R.id.rl_details_loan);
        rlListLoan = findViewById(R.id.rl_list_loan);
        detailSubmit = findViewById(R.id.bt_detail_submit);
        tvWeb = findViewById(R.id.tv_web);
        view2.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        tvTitle.setText(R.string.loan_list_name);
        token = SharedPreUtil.readString(this, SharedPreUtil.TOKEN, "");
        names = getResources().getStringArray(R.array.detail);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        loanListBean = (LoanListBean) bundle.getSerializable("bean");
        payList = new ArrayList<>();
        listValue = new ArrayList<>();
        if (loanListBean != null) {
            tvMoneyNumber.setText(FileSizeUtil.getStringNumber(loanListBean.getAmount() + ""));
            tvDayTime.setText(loanListBean.getPeriod() + (loanListBean.getPeriodUnit().equals
                    ("D") ? "hari" : loanListBean.getPeriodUnit().equals("W") ? "minggu" :
                    loanListBean.getPeriodUnit().equals("M") ? "bulan" : "tahun"));
            statusLogs = loanListBean.getStatusLogs();
            listValue.add(loanListBean.getCredentialNo());
            listValue.add(loanListBean.getCreateTime());
            listValue.add(FileSizeUtil.getStringNumber(loanListBean.getAmount() + ""));
            listValue.add(loanListBean.getPeriod() + (loanListBean.getPeriodUnit().equals("D") ?
                    "hair" : loanListBean.getPeriodUnit().equals("W") ? "minggu" : loanListBean
                    .getPeriodUnit().equals("M") ? "bulan" : "tahun"));
            listValue.add("Rp " + loanListBean.getCost());
            listValue.add(FileSizeUtil.getStringNumber(loanListBean.getPunishmentAmount() + ""));
            listValue.add(FileSizeUtil.getStringNumber(loanListBean.getRemainAmount() + ""));
            listValue.add(loanListBean.getRepaymentType());
            listValue.add(TextUtils.isEmpty(loanListBean.getBankCode() + "") ? "_" : loanListBean
                    .getBankCode());
            listValue.add(TextUtils.isEmpty(loanListBean.getCardNo() + "") ? "_" : loanListBean
                    .getCardNo());
            if (statusLogs.size() == 1) {
                linearLayout.addView(createView(statusLogs.size(), true, true, true, true,
                        statusLogs.get(0).getStatus()));
            } else if (statusLogs.size() > 1) {
                for (int i = 0; i < statusLogs.size(); i++) {
                    if (statusLogs.size() == 0) {
                        linearLayout.addView(createView(4, true, true, true, true, statusLogs.get
                                (i).getStatus()));
                    } else if (i == 0) {
                        linearLayout.addView(createView(4, true, true, true, false, statusLogs
                                .get(i).getStatus()));
                    } else if (i == statusLogs.size() - 1) {
                        linearLayout.addView(createView(4, true, true, false, true, statusLogs
                                .get(i).getStatus()));
                    } else {
                        linearLayout.addView(createView(4, true, true, false, false, statusLogs
                                .get(i).getStatus()));
                    }
                }
            }
            View view_top = LayoutInflater.from(this).inflate(R.layout.list_top_layout, null);
            View view_bottom = LayoutInflater.from(this).inflate(R.layout.list_bottom_layout, null);
            TextView tvTop = view_top.findViewById(R.id.tv_top);
            TextView tvBottom = view_bottom.findViewById(R.id.tv_bottom);
            tvTop.setText(loanListBean.getLoanAppId() + "");
            tvBottom.setText(loanListBean.getDueDate() + "");
            listView.addHeaderView(view_top);
            listView.addFooterView(view_bottom);
            detailListAdapter = new DetailListAdapter(mContext, listValue, names);
            listView.setAdapter(detailListAdapter);
            String status = loanListBean.getStatus();
            checkStatus(status);
        }
    }

    private void checkStatus(String status) {
        if (!TextUtils.isEmpty(status)) {
            //已提交、补录、审核中
            if ("SUBMITTED".equals(status) || "SUPPLEMENT".equals(status) || "IN_REVIEW".equals
                    (status)) {
                detailSubmit.setText("Untuk membatalkan pinjaman");
                detailSubmit.setBackgroundResource(R.drawable.send_again_style);
                detailSubmit.setEnabled(true);
                //审核不通过
            } else if ("REJECTED".equals(status)) {
                detailSubmit.setText("Ditolak");
                detailSubmit.setBackgroundResource(R.drawable.login_next_style);
                detailSubmit.setEnabled(false);
                //用户取消、管理员取消
            } else if ("WITHDRAWN".equals(status) || "CLOSED".equals(status)) {
                detailSubmit.setText("Telah dibatalkan");
                detailSubmit.setBackgroundResource(R.drawable.login_next_style);
                detailSubmit.setEnabled(false);
                //放款中
            } else if ("ISSUING".equals(status)) {
                detailSubmit.setText("Sedang Mengeluarkan Pinjaman");
                detailSubmit.setBackgroundResource(R.drawable.login_next_style);
                detailSubmit.setEnabled(false);
            } else if ("CURRENT".equals(status) || "OVERDUE".equals(status)) {
                detailSubmit.setText("Berlaku untuk pembayaran");
                detailSubmit.setBackgroundResource(R.drawable.send_again_style);
                detailSubmit.setEnabled(true);
                //放款失败
            } else if ("ISSUE_FAILED".equals(status)) {
                detailSubmit.setText("Pengeluaran Pinjaman Gagal");
                detailSubmit.setBackgroundResource(R.drawable.login_next_style);
                detailSubmit.setEnabled(false);
                //已结清
            } else if ("PAID_OFF".equals(status)) {
                detailSubmit.setText("Lunas");
                detailSubmit.setBackgroundResource(R.drawable.login_next_style);
                detailSubmit.setEnabled(false);
            } else {
                detailSubmit.setText(status);
                detailSubmit.setBackgroundResource(R.drawable.login_next_style);
                detailSubmit.setEnabled(false);
            }
        }
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        tvWeb.setOnClickListener(this);
        detailSubmit.setOnClickListener(this);
        rlLoanDetail.setOnClickListener(this);
        rlListLoan.setOnClickListener(this);
    }

    public AuditProgressView createView(int stepCount, boolean isCurrentComplete, boolean
            isNextComplete, boolean isFirstStep, boolean isLastStep, String text) {
        AuditProgressView view = new AuditProgressView(this);
        view.setStepCount(stepCount);
        view.setIsCurrentComplete(isCurrentComplete);
        view.setIsNextComplete(isNextComplete);
        view.setIsFirstStep(isFirstStep);
        view.setIsLastStep(isLastStep);
        view.setText(text);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.rl_details_loan:
                tvView1.setTextColor(getResources().getColor(R.color.green));
                view1.setVisibility(View.VISIBLE);
                tvView2.setTextColor(getResources().getColor(R.color.center_grey));
                view2.setVisibility(View.GONE);
                payListView.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_list_loan:
                tvView2.setTextColor(getResources().getColor(R.color.green));
                view2.setVisibility(View.VISIBLE);
                tvView1.setTextColor(getResources().getColor(R.color.center_grey));
                view1.setVisibility(View.GONE);
                scrollView.setVisibility(View.GONE);
                payListView.setVisibility(View.VISIBLE);
                getListData();
                break;
            case R.id.bt_detail_submit:
                String status = loanListBean.getStatus();
                if ("CURRENT".equals(status) || "OVERDUE".equals(status)) {
                    Intent intent = new Intent(LoanDetailActivity.this, PayLoanActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", loanListBean);
                    intent.putExtra("bundle", bundle);
                    startActivity(intent);
                } else if ("SUBMITTED".equals(status) || "SUPPLEMENT".equals(status) ||
                        "IN_REVIEW".equals(status)) {
                    exitDialog();
                }
                break;
            case R.id.tv_web:
                Intent loancontract = new Intent(LoanDetailActivity.this, WebActivity.class);
                loancontract.putExtra("url", Contants.LOANCONTRACT);
                loancontract.putExtra("title", getResources().getString(R.string.loancontract));
                startActivity(loancontract);
                break;
            default:
                break;
        }
    }

    private void exitDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view_dialog = layoutInflater.inflate(R.layout.layout_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setContentView(view_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView dialogTitle = (TextView) view_dialog.findViewById(R.id.dialog_title);
        TextView dialogDetails = (TextView) view_dialog.findViewById(R.id.dialog_details);
        TextView dialogCancel = (TextView) view_dialog.findViewById(R.id.dialog_cancel);
        TextView dialogSure = (TextView) view_dialog.findViewById(R.id.dialog_sure);
        dialogDetails.setText("Apakah Anda yakin mau \n" + "membatalkan pinjaman Anda?");
        dialogSure.setText("Kamu yakin");
        dialogCancel.setText("Batal");
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                dialog.dismiss();
            }
        });
    }

    public void cancel() {
        LoadingCustom.showprogress(LoanDetailActivity.this, "", true);
        OkHttpClient okHttpClient = new OkHttpClient();
//     ////        Post请求
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("loanAppId", loanListBean.getLoanAppId() + "");
        RequestBody formBody = builder.build();
        Request request1 = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.CANCEL).post(formBody).build();
        Call call1 = okHttpClient.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(5);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                handler.sendEmptyMessage(4);
                Log.e("LoanDetailActivity", respones + "Post");
            }
        });
    }

    public void getListData() {
        LoadingCustom.showprogress(this, "", true);
        OkHttpClient okHttpClient = new OkHttpClient();
//     ////        Post请求
        String url = Contants.DEPOSITLIST + "?loanAppId=" + loanListBean.getLoanAppId();
        Request request1 = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url(url)
                .build();
        Call call1 = okHttpClient.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(5);
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
                Log.e("LoanDetailActivity", respones + "Post");
            }
        });
    }
}
