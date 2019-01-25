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
import android.widget.TextView;

import com.rupiah.flash.pros.R;
import com.rupiah.flash.pros.adapter.CityAdapter;
import com.rupiah.flash.pros.bean.ProvinceBean;
import com.rupiah.flash.pros.utils.Contants;
import com.rupiah.flash.pros.utils.StatusBarUtils;
import com.rupiah.flash.pros.view.LoadingCustom;
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
 * 街道地址
 */
public class AreaActivity extends BasicActivity {
    TextView tvTitle;
    ImageView ivBack;
    ListView listView;
    List<ProvinceBean.RegionsBean> list;
    CityAdapter adapter;
    String areaUrl ="";
    int provinceId, cityId, districtId, areaId;
    String provinceName, cityName, districtName, areaName;
    @SuppressLint("HandlerLeak")
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
                    adapter = new CityAdapter(list, AreaActivity.this);
                    listView.setAdapter(adapter);
                }
            }
        }
    };

    @Override
    protected int getResourceId() {
        return R.layout.activity_area;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        StatusBarUtils.setWindowStatusBarColor(this, R.color.tab_bg, StatusBarUtils.Position.STATUS);
    }

    @Override
    protected void initView() {
        tvTitle = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_title_back);
        ivBack.setVisibility(View.VISIBLE);
        listView = findViewById(R.id.area_list);
    }

    @Override
    protected void initData() {
        provinceId = getIntent().getIntExtra("provinceId", 0);
        provinceName = getIntent().getStringExtra("provinceName");
        cityId = getIntent().getIntExtra("cityId", 0);
        cityName = getIntent().getStringExtra("cityName");
        districtId = getIntent().getIntExtra("districtId", 0);
        districtName = getIntent().getStringExtra("districtName");
        areaUrl = Contants.AREA + "/" + districtId;
        Log.e("AreaActivity", districtId + "");
        tvTitle.setText(R.string.area);
        list = new ArrayList<>();
        getProvince();
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
                Intent intent = new Intent(AreaActivity.this, ProvinceActivity.class);
                areaId = list.get(position).getId();
                areaName = list.get(position).getName();
                intent.putExtra("provinceId", provinceId);
                intent.putExtra("provinceName", provinceName);
                intent.putExtra("cityId", cityId);
                intent.putExtra("cityName", cityName);
                intent.putExtra("districtId", districtId);
                intent.putExtra("districtName", districtName);
                intent.putExtra("areaId", areaId);
                intent.putExtra("areaName", areaName);
                setResult(1, intent);
                finish();
            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(AreaActivity.this, ProvinceActivity.class);
//        intent.putExtra("provinceId", provinceId);
//        intent.putExtra("provinceName", provinceName);
//        intent.putExtra("cityId", cityId);
//        intent.putExtra("cityName", cityName);
//        intent.putExtra("districtId", districtId);
//        intent.putExtra("districtName", districtName);
//        intent.putExtra("areaId", areaId);
//        intent.putExtra("areaName", areaName);
//        setResult(1, intent);
//        finish();
//    }

    public void getProvince() {
        LoadingCustom.showprogress(mContext, "", true);
        OkHttpClient okHttpClient = new OkHttpClient();
//        get 请求
        Request request = new Request.Builder().url(areaUrl).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
               handler.sendEmptyMessage(1);
                Log.e("AreaActivity", e.getMessage() + "失败");
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
}
