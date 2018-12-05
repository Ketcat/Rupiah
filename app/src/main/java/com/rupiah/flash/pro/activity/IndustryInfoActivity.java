package com.rupiah.flash.pro.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.rupiah.flash.pro.R;
import com.rupiah.flash.pro.adapter.DialogListAdapter;
import com.rupiah.flash.pro.bean.ErrorBean;
import com.rupiah.flash.pro.bean.IndustryBean;
import com.rupiah.flash.pro.bean.UrlBean;
import com.rupiah.flash.pro.utils.Contants;
import com.rupiah.flash.pro.utils.SharedPreUtil;
import com.rupiah.flash.pro.utils.StatusBarUtils;
import com.rupiah.flash.pro.view.LoadingCustom;

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

/**
 * 行业信息页面
 */
public class IndustryInfoActivity extends BasicActivity implements View.OnClickListener {
    TextView tvTitle, tvWorkType, tvInCome, tvCompanyAddress;
    ImageView ivBack, ivAddIndustry, ivOther;
    RelativeLayout rlWorkType, rlInCome, rlCompanyAddress;
    EditText etCompanyName, etAddressDetails, etCompanyPhone;
    Button btSend;
    List<String> list;
    String industryUrl;
    int provinceId, areaId, cityId, districtId;
    String provinceName, cityName, districtName, areaName, token;
    String[] workType, salary;
    String[] workTypeValue, salaryValue;
    String[] permissions = new String[]{"android.permission.CAMERA", "android.permission" + "" +
            ".WRITE_EXTERNAL_STORAGE"};
    Intent intent;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadingCustom.dismissprogress();
            String result = msg.getData().getString("result");
            Gson gson = new Gson();
            switch (msg.what) {
                case 1:
                    try {
                        if (!TextUtils.isEmpty(result)) {
                            UrlBean urlBean = gson.fromJson(result, UrlBean.class);

                            if (urlBean.getFiles().size() > 0) {
                                industryUrl = urlBean.getFiles().get(0).getUrl();
                                Glide.with(IndustryInfoActivity.this).load(urlBean.getFiles().get
                                        (0).getUrl()).error(R.mipmap.ic_launcher).placeholder(R
                                        .mipmap.ic_launcher).into(ivAddIndustry);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        if (!TextUtils.isEmpty(result)) {
                            IndustryBean industryBean = gson.fromJson(result, IndustryBean.class);
                            tvWorkType.setText(selectValue(workTypeValue, workType, industryBean
                                    .getProfession()));
                            tvInCome.setText(selectValue(salaryValue, salary, industryBean
                                    .getSalary()));
                            etCompanyName.setText(industryBean.getCompanyName());
                            tvCompanyAddress.setText(industryBean.getCompanyProvince() +
                                    industryBean.getCompanyCity() + industryBean
                                    .getCompanyDistrict() + industryBean.getCompanyArea());
                            etAddressDetails.setText(industryBean.getCompanyAddress());
                            etCompanyPhone.setText(industryBean.getCompanyPhone());
                            provinceName = industryBean.getCompanyProvince() == null ? "" :
                                    industryBean.getCompanyProvince();
                            cityName = industryBean.getCompanyCity() == null ? "" : industryBean
                                    .getCompanyCity();
                            districtName = industryBean.getCompanyDistrict() == null ? "" :
                                    industryBean.getCompanyDistrict();
                            areaName = industryBean.getCompanyArea() == null ? "" : industryBean
                                    .getCompanyArea();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        if (!TextUtils.isEmpty(result)) {
                            ErrorBean errorBean = gson.fromJson(result, ErrorBean.class);
                            Toast.makeText(mContext, errorBean.getMessage(), Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Toast.makeText(mContext, R.string.replace_success, Toast
                                    .LENGTH_SHORT).show();
                            Intent intent = new Intent(Contants.action);
                            intent.putExtra("isShow", false);
                            mContext.sendBroadcast(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        if (!TextUtils.isEmpty(result)) {
                            UrlBean urlBean = gson.fromJson(result, UrlBean.class);
                            if (urlBean.getFiles().size() > 0) {
                                Glide.with(IndustryInfoActivity.this).load(urlBean.getFiles().get
                                        (0).getUrl()).error(R.mipmap.ic_launcher).placeholder(R
                                        .mipmap.ic_launcher).into(ivOther);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

    };

    @Override
    protected int getResourceId() {
        return R.layout.activity_industry_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        StatusBarUtils.setWindowStatusBarColor(IndustryInfoActivity.this, R.color.tab_bg,
                StatusBarUtils.Position.STATUS);
    }

    @Override
    protected void initView() {
        tvTitle = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_title_back);
        ivAddIndustry = findViewById(R.id.iv_add_industry);
        rlWorkType = findViewById(R.id.rl_work_type);
        tvWorkType = findViewById(R.id.tv_work_type_value);
        tvInCome = findViewById(R.id.tv_income_value);
        tvCompanyAddress = findViewById(R.id.tv_company_address_value);
        rlInCome = findViewById(R.id.rl_income);
        rlCompanyAddress = findViewById(R.id.rl_company_address);
        etCompanyName = findViewById(R.id.et_tv_company_name_value);
        etAddressDetails = findViewById(R.id.et_tv_company_address_details_value);
        etCompanyPhone = findViewById(R.id.et_tv_company_phone_number_value);
        ivOther = findViewById(R.id.iv_add_other);
        btSend = findViewById(R.id.bt_industry_send);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.industry_info);
        token = SharedPreUtil.readString(this, SharedPreUtil.TOKEN, "");
        intent = new Intent(IndustryInfoActivity.this, MyCameraActivity.class);
        ivBack.setVisibility(View.VISIBLE);
        workType = getResources().getStringArray(R.array.work_type_yn);
        salary = getResources().getStringArray(R.array.salary);
        workTypeValue = getResources().getStringArray(R.array.work_type_yy);
        salaryValue = getResources().getStringArray(R.array.salary_value);
        getUserInfo();
    }

    @Override
    protected void initListener() {
        rlWorkType.setOnClickListener(this);
        rlInCome.setOnClickListener(this);
        rlCompanyAddress.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btSend.setOnClickListener(this);
        ivOther.setOnClickListener(this);
        ivAddIndustry.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_work_type:
                selectType(workType, tvWorkType);
                break;
            case R.id.rl_income:
                selectType(salary, tvInCome);
                break;
            case R.id.rl_company_address:
                startActivityForResult(new Intent(IndustryInfoActivity.this, ProvinceActivity
                        .class), 1);
                break;
            case R.id.iv_add_other:
                intent.putExtra("type", 3);
                checkPermiss();
                break;
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.bt_industry_send:
                String companyName = etCompanyName.getText().toString().trim();
                String companyAddress = etAddressDetails.getText().toString().trim();
                String companyPhone = etCompanyPhone.getText().toString().trim();
                String profession = selectValue(workType, workTypeValue, tvWorkType.getText()
                        .toString().trim());
                String salarys = selectValue(salary, salaryValue, tvInCome.getText().toString()
                        .trim());
                if (!TextUtils.isEmpty(companyName) && !TextUtils.isEmpty(companyAddress) &&
                        !TextUtils.isEmpty(companyPhone) && !TextUtils.isEmpty(profession) &&
                        !TextUtils.isEmpty(salarys) && !TextUtils.isEmpty(provinceName) &&
                        !TextUtils.isEmpty(cityName) && !TextUtils.isEmpty(districtName) &&
                        !TextUtils.isEmpty(areaName) && !TextUtils.isEmpty(industryUrl)) {
                    putUserInfo(companyName, companyAddress, companyPhone, profession, salarys);
                } else {
                    Toast.makeText(mContext, R.string.no_all_info, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_add_industry:
                intent.putExtra("type", 1);
                upWorkPhoto();
                break;
            default:
                break;
        }
    }


    private void selectType(String[] strs, TextView view) {
        if (list == null) {
            list = new ArrayList<>();
        } else {
            list.clear();
        }
        for (int i = 0; i < strs.length; i++) {
            list.add(strs[i]);
        }
        listDialog(view);
    }

    public String selectValue(String[] strs, String[] strsValue, String textValue) {
        for (int i = 0; i < strs.length; i++) {
            if (textValue.equals(strs[i])) {
                return strsValue[i];
            }
        }
        return "";
    }

    private void listDialog(final TextView textView) {
        final LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view_dialog = layoutInflater.inflate(R.layout.layout_list_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setContentView(view_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ListView listView = view_dialog.findViewById(R.id.dialog_list);
        DialogListAdapter adapter = new DialogListAdapter(list, mContext);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                textView.setText(list.get(position));
            }
        });
    }

    private void upWorkPhoto() {
        final LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view_dialog = layoutInflater.inflate(R.layout.layout_industry_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setContentView(view_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btWorkSure = view_dialog.findViewById(R.id.bt_work_sure);
        btWorkSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                checkPermiss();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && data != null) {
            provinceId = data.getIntExtra("provinceId", 0);
            provinceName = data.getStringExtra("provinceName");
            cityId = data.getIntExtra("cityId", 0);
            cityName = data.getStringExtra("cityName");
            districtId = data.getIntExtra("districtId", 0);
            districtName = data.getStringExtra("districtName");
            areaId = data.getIntExtra("areaId", 0);
            areaName = data.getStringExtra("areaName");
            tvCompanyAddress.setText(provinceName + " " + cityName + " " + districtName + " " +
                    areaName);
        }
    }

    /**
     * 请求权限
     */
    private void checkPermiss() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            startActivity(intent);
            return;
        }
        List<String> deniedPerms = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < permissions.length; i++) {
                if (PackageManager.PERMISSION_GRANTED != checkSelfPermission(permissions[i])) {
                    deniedPerms.add(permissions[i]);
                }
            }
            int denyPermNum = deniedPerms.size();
            if (denyPermNum != 0) {
                requestPermissions(deniedPerms.toArray((new String[denyPermNum])), Contants
                        .REQUEST_PERMISSION_CODE_TAKE_PIC);
            } else {
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(token)) {
            getPhotoInfo();
            getOtherPhoto();
        }
    }

    public void getPhotoInfo() {
        LoadingCustom.showprogress(this, "", true);
        OkHttpClient okHttpClient = new OkHttpClient();
//        get 请求
        Request request = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.GET_EMPLOY_PHOTO).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(4);
                Log.e("IndustryInfoActivity", e.getMessage() + "失败");
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
                Log.e("IndustryInfoActivity", respones);
            }
        });
    }

    public void getOtherPhoto() {
        OkHttpClient okHttpClient = new OkHttpClient();
//        get 请求
        Request request = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.GET_OTHER_PHOTO + "?type=OTHER_PHOTO").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(4);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", respones);
                message.setData(bundle);
                message.what = 4;
                handler.sendMessage(message);
                Log.e("IndustryInfoActivity", respones);
            }
        });
    }

    public void getUserInfo() {
        OkHttpClient okHttpClient = new OkHttpClient();
//        get 请求
        Request request = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.EMPLOYMENT).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(4);
                Log.e("IndustryInfoActivity", e.getMessage() + "失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", respones);
                message.setData(bundle);
                message.what = 2;
                handler.sendMessage(message);
                Log.e("IndustryInfoActivity", respones);
            }
        });
    }

    public void putUserInfo(String companyName, String companyAddress, String companyPhone,
                            String profession, String salary) {
        LoadingCustom.showprogress(this, "", true);
        OkHttpClient okHttpClient = new OkHttpClient();
//     ////        Post请求
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("companyName", companyName);
        builder.add("companyProvince", provinceName);
        builder.add("companyCity", cityName);
        builder.add("companyDistrict", districtName);
        builder.add("companyArea", areaName);
        builder.add("companyAddress", companyAddress);
        builder.add("companyPhone", companyPhone);
        builder.add("profession", profession);
        builder.add("salary", salary);
        RequestBody formBody = builder.build();
        Request request1 = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.EMPLOYMENT).put(formBody).build();
        Call call1 = okHttpClient.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(4);
                Log.e("IndustryInfoActivity", e.getMessage() + "失败");
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
                Log.e("IndustryInfoActivity", respones + "Post");
            }
        });
    }
}
