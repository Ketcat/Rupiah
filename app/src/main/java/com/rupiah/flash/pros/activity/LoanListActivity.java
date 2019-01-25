package com.rupiah.flash.pros.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rupiah.flash.pros.R;
import com.rupiah.flash.pros.adapter.LoanListAdapter;
import com.rupiah.flash.pros.bean.LoanListBean;
import com.rupiah.flash.pros.utils.Contants;
import com.rupiah.flash.pros.utils.SharedPreUtil;
import com.rupiah.flash.pros.utils.StatusBarUtils;
import com.rupiah.flash.pros.view.LoadingCustom;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoanListActivity extends BasicActivity {
    TextView tvTitle;
    ImageView ivBack;
    ListView listView;
    String token;
    List<LoanListBean> loanListBean;
    LoanListAdapter adapter;
    RelativeLayout rlNoInfo;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadingCustom.dismissprogress();
            String result = msg.getData().getString("result");
            if (!TextUtils.isEmpty(result)) {
                try {
                    Gson gson = new Gson();
                    loanListBean = gson.fromJson(result, new TypeToken<List<LoanListBean>>() {
                    }.getType());
                    if (loanListBean.size()>0) {
                        adapter = new LoanListAdapter(LoanListActivity.this, loanListBean);
                        listView.setAdapter(adapter);
                        rlNoInfo.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                    }else {
                        rlNoInfo.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    rlNoInfo.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
            } else {
                rlNoInfo.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected int getResourceId() {
        return R.layout.activity_loan_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        StatusBarUtils.setWindowStatusBarColor(LoanListActivity.this, R.color.tab_bg,
                StatusBarUtils.Position.STATUS);

    }

    @Override
    protected void initView() {
        tvTitle = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_title_back);
        listView = findViewById(R.id.loan_list);
        rlNoInfo = findViewById(R.id.rl_no_info);
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.loan_list_name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        token = SharedPreUtil.readString(this, SharedPreUtil.TOKEN, "");
        if (loanListBean == null) loanListBean = new ArrayList<>();
        if (!TextUtils.isEmpty(token)) {
            getAllList();
        } else {
            rlNoInfo.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LoanListActivity.this, LoanDetailActivity.class);
                LoanListBean bean = loanListBean.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", bean);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
    }

    public void getAllList() {
        LoadingCustom.showprogress(this, "", true);
        OkHttpClient okHttpClient = new OkHttpClient();
//        get 请求
        Request request = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.ALL).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", "");
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", respones);
                message.setData(bundle);
                handler.sendMessage(message);
                Log.e("LoanListActivity", "success" + respones);
            }
        });
    }
}
