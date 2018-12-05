package com.rupiah.flash.pro.activity;

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
import android.widget.TextView;

import com.rupiah.flash.pro.R;
import com.rupiah.flash.pro.adapter.CityAdapter;
import com.rupiah.flash.pro.bean.ProvinceBean;
import com.rupiah.flash.pro.utils.Contants;
import com.rupiah.flash.pro.utils.StatusBarUtils;
import com.rupiah.flash.pro.view.LoadingCustom;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 市区地址
 */
public class CityActivity extends BasicActivity {


    TextView tvTitle;
    ImageView ivBack;
    ListView listView;
    List<ProvinceBean.RegionsBean> list;
    CityAdapter adapter;
    String provinceUrl = "";
    int provinceId;
    String provinceName;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadingCustom.dismissprogress();
            String result = msg.getData().getString("result");
            if (!TextUtils.isEmpty(result)) {
                Gson gson = new Gson();
                ProvinceBean provinceBean = gson.fromJson(result, ProvinceBean.class);
                list.addAll(provinceBean.getRegions());
                if (list.size() > 0) {
                    adapter = new CityAdapter(list, CityActivity.this);
                    listView.setAdapter(adapter);
                }
            }
        }
    };

    @Override
    protected int getResourceId() {
        return R.layout.activity_city;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        StatusBarUtils.setWindowStatusBarColor(this, R.color.tab_bg, StatusBarUtils.Position
                .STATUS);
    }

    @Override
    protected void initView() {
        tvTitle = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_title_back);
        ivBack.setVisibility(View.VISIBLE);
        listView = findViewById(R.id.city_list);
    }

    @Override
    protected void initData() {
        provinceId = getIntent().getIntExtra("provinceId", 0);
        provinceName = getIntent().getStringExtra("provinceName");
        provinceUrl = Contants.CITY + "/" + provinceId;
        tvTitle.setText(R.string.city);
        list = new ArrayList<>();
        getProvince(provinceUrl);
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
                Intent intent = new Intent(CityActivity.this, DistrictActivity.class);
                intent.putExtra("provinceId", provinceId);
                intent.putExtra("provinceName", provinceName);
                intent.putExtra("cityId", list.get(position).getId());
                intent.putExtra("cityName", list.get(position).getName());
                startActivityForResult(intent, 1);
            }
        });
    }

    public void getProvince(String url) {
        LoadingCustom.showprogress(mContext, "", true);
        OkHttpClient okHttpClient = new OkHttpClient();
//        get 请求
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(1);
                Log.e("CityActivity", e.getMessage() + "失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", respones);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && data != null) {
            setResult(1, data);
            finish();
        }
    }
}
