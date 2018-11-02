package com.flash.rupiah.pro.activity;

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

import com.flash.rupiah.pro.R;
import com.flash.rupiah.pro.adapter.CityAdapter;
import com.flash.rupiah.pro.bean.ProvinceBean;
import com.flash.rupiah.pro.utils.Contants;
import com.flash.rupiah.pro.utils.StatusBarUtils;
import com.flash.rupiah.pro.view.LoadingCustom;
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
 * 县级地址
 */
public class DistrictActivity extends BasicActivity {

    TextView tvTitle;
    ImageView ivBack;
    ListView listView;
    List<ProvinceBean.RegionsBean> list;
    CityAdapter adapter;
    String districtUrl = "";
    int provinceId, cityId;
    String provinceName, cityName;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.getData().getString("result");
            LoadingCustom.dismissprogress();
            if (!TextUtils.isEmpty(result)) {
                Gson gson = new Gson();
                ProvinceBean provinceBean = gson.fromJson(result, ProvinceBean.class);
                list.addAll(provinceBean.getRegions());
                if (list.size() > 0) {
                    adapter = new CityAdapter(list, DistrictActivity.this);
                    listView.setAdapter(adapter);
                }
            }
        }
    };

    @Override
    protected int getResourceId() {
        return R.layout.activity_district;
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
        listView = findViewById(R.id.district_list);
    }

    @Override
    protected void initData() {
        provinceId = getIntent().getIntExtra("provinceId", 0);
        provinceName = getIntent().getStringExtra("provinceName");
        cityId = getIntent().getIntExtra("cityId", 0);
        cityName = getIntent().getStringExtra("cityName");
        districtUrl = Contants.DISTRICT + "/" + cityId;
        tvTitle.setText(R.string.district);
        list = new ArrayList<>();
        getProvince(districtUrl);
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
                Intent intent = new Intent(DistrictActivity.this, AreaActivity.class);
                intent.putExtra("provinceId", provinceId);
                intent.putExtra("provinceName", provinceName);
                intent.putExtra("cityId", cityId);
                intent.putExtra("cityName", cityName);
                intent.putExtra("districtId", list.get(position).getId());
                intent.putExtra("districtName", list.get(position).getName());
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
                Log.e("DistrictActivity", e.getMessage() + "失败");
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
