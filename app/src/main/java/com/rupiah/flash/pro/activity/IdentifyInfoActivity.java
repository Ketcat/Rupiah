package com.rupiah.flash.pro.activity;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rupiah.flash.pro.R;
import com.rupiah.flash.pro.adapter.DialogListAdapter;
import com.rupiah.flash.pro.bean.ErrorBean;
import com.rupiah.flash.pro.bean.UserInfoBean;
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
 * 识别信息页面
 */
public class IdentifyInfoActivity extends BasicActivity implements View.OnClickListener {
    TextView tvTitle, tvUserName, tvCardCode, tvSex, tvEduGradeValue, tvMaritalStatusValue,
            tvSunNumberValue, tvContactAddressValue, tvCheckInTimeValue, tvSocialNameValue,
            tvSocialIdValue;
    ImageView ivBack;
    //    EditText etMotherName;
    EditText etAddressDetails;
    RelativeLayout rlEduGrade, rlMaritalStatus, rlSunNumber, rlContactAddress, rlCheckInTime,
            rlSocialName;
    List<String> list;
    Button btSend;
    int provinceId, areaId, cityId, districtId;
    String provinceName, cityName, districtName, areaName, token;
    String[] marryState, edu, childNumber, times, socialNames;
    String[] marryValue, childNumberValue, timesValue;
    UserInfoBean userInfoBean;
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
                            userInfoBean = gson.fromJson(result, UserInfoBean.class);
                            if (userInfoBean != null) {
                                provinceName = userInfoBean.getProvince() == null ? "" :
                                        userInfoBean.getProvince();
                                cityName = userInfoBean.getCity() == null ? "" : userInfoBean
                                        .getCity();
                                districtName = userInfoBean.getDistrict() == null ? "" :
                                        userInfoBean.getDistrict();
                                areaName = userInfoBean.getArea() == null ? "" : userInfoBean
                                        .getArea();
                                tvUserName.setText(userInfoBean.getFullName());
                                tvCardCode.setText(userInfoBean.getCredentialNo());
                                tvSex.setText(userInfoBean.getGender());
//                                etMotherName.setText(userInfoBean.getFamilyNameInLaw());
                                tvEduGradeValue.setText(userInfoBean.getLastEducation());
                                tvMaritalStatusValue.setText(selectValue(marryValue, marryState,
                                        userInfoBean.getMaritalStatus()));
                                tvSunNumberValue.setText(selectValue(childNumberValue,
                                        childNumber, userInfoBean.getChildrenNumber()));
                                tvContactAddressValue.setText(provinceName + " " + cityName + " "
                                        + cityName + " " + areaName);
                                etAddressDetails.setText(userInfoBean.getAddress());
                                tvCheckInTimeValue.setText(selectValue(timesValue, times,
                                        userInfoBean.getResidenceDuration()));
                                tvSocialNameValue.setText(userInfoBean.getSocialName() == null ?
                                        "" : userInfoBean.getSocialName());
                                tvSocialIdValue.setText(userInfoBean.getFacebookId() == null ? ""
                                        : userInfoBean.getFacebookId());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
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
                            startActivity(new Intent(IdentifyInfoActivity.this,
                                    ContactInfoActivity.class));
                            finish();
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
        return R.layout.activity_identify_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        StatusBarUtils.setWindowStatusBarColor(IdentifyInfoActivity.this, R.color.tab_bg,
                StatusBarUtils.Position.STATUS);
    }

    @Override
    protected void initView() {
        tvTitle = findViewById(R.id.tv_title);
        tvUserName = findViewById(R.id.tv_user_name);
        tvCardCode = findViewById(R.id.tv_card_code);
        tvSex = findViewById(R.id.tv_user_sex);
        tvEduGradeValue = findViewById(R.id.tv_edu_grade_value);
        tvMaritalStatusValue = findViewById(R.id.tv_marital_status_value);
        tvSunNumberValue = findViewById(R.id.tv_sun_number_value);
        tvContactAddressValue = findViewById(R.id.tv_contact_address_value);
        tvCheckInTimeValue = findViewById(R.id.tv_check_in_time_value);
        tvSocialNameValue = findViewById(R.id.tv_social_name_value);
        tvSocialIdValue = findViewById(R.id.tv_social_id_value);
        ivBack = findViewById(R.id.iv_title_back);
        rlEduGrade = findViewById(R.id.rl_edu_grade);
        rlMaritalStatus = findViewById(R.id.rl_marital_status);
        rlSunNumber = findViewById(R.id.rl_sun_number);
        rlContactAddress = findViewById(R.id.rl_contact_address);
        rlCheckInTime = findViewById(R.id.rl_check_in_time);
        rlSocialName = findViewById(R.id.rl_social_name);
//        etMotherName = findViewById(R.id.et_mother_name);
        etAddressDetails = findViewById(R.id.et_address_details);
        btSend = findViewById(R.id.identify_send);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.identify_info);
        token = SharedPreUtil.readString(this, SharedPreUtil.TOKEN, "");
        ivBack.setVisibility(View.VISIBLE);
        marryState = getResources().getStringArray(R.array.marry_list);
        marryValue = getResources().getStringArray(R.array.marry_state);
        edu = getResources().getStringArray(R.array.edu);
        childNumber = getResources().getStringArray(R.array.child_number);
        childNumberValue = getResources().getStringArray(R.array.child_number_value);
        times = getResources().getStringArray(R.array.time);
        timesValue = getResources().getStringArray(R.array.time_value);
        socialNames = getResources().getStringArray(R.array.socialNames);
        getUserInfo();
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        rlEduGrade.setOnClickListener(this);
        rlMaritalStatus.setOnClickListener(this);
        rlSunNumber.setOnClickListener(this);
        rlContactAddress.setOnClickListener(this);
        rlCheckInTime.setOnClickListener(this);
        rlSocialName.setOnClickListener(this);
        btSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.rl_edu_grade:
                selectType(edu, tvEduGradeValue);
                break;
            case R.id.rl_marital_status:
                selectType(marryState, tvMaritalStatusValue);
                break;
            case R.id.rl_sun_number:
                selectType(childNumber, tvSunNumberValue);
                break;
            case R.id.rl_social_name:
                selectType(socialNames, tvSocialNameValue);
                break;
            case R.id.rl_contact_address:
                startActivityForResult(new Intent(IdentifyInfoActivity.this, ProvinceActivity
                        .class), 1);
                break;
            case R.id.rl_check_in_time:
                selectType(times, tvCheckInTimeValue);
                break;
            case R.id.identify_send:
                String fullName = tvUserName.getText().toString().trim();
                String gender = tvSex.getText().toString().trim();
//                String familyNameInLaw = etMotherName.getText().toString().trim();
                String lastEducation = tvEduGradeValue.getText().toString().trim();
                String maritalStatus = selectValue(marryState, marryValue, tvMaritalStatusValue
                        .getText().toString().trim());
                String childrenNumber = selectValue(childNumber, childNumberValue,
                        tvSunNumberValue.getText().toString().trim());
                String address = etAddressDetails.getText().toString().trim();
                String residenceDuration = selectValue(times, timesValue, tvCheckInTimeValue
                        .getText().toString().trim());
                String socialName = tvSocialNameValue.getText().toString().trim();
                String socialId = tvSocialIdValue.getText().toString().trim();
                if (!TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(gender) && !TextUtils
                        .isEmpty(lastEducation) && !TextUtils.isEmpty(maritalStatus) &&
                        !TextUtils.isEmpty(childrenNumber) && !TextUtils.isEmpty(address) &&
                        !TextUtils.isEmpty(residenceDuration) && !TextUtils.isEmpty(provinceName)
                        && !TextUtils.isEmpty(cityName) && !TextUtils.isEmpty(areaName)) {
                    putInfo(fullName, gender, lastEducation, maritalStatus, childrenNumber,
                            address, residenceDuration, "", socialName, socialId);
                } else {
                    Toast.makeText(mContext, R.string.no_all_info, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void eduGradeDialog(final TextView textView) {
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

    private void selectType(String[] strs, TextView view) {
        if (list == null) {
            list = new ArrayList<>();
        } else {
            list.clear();
        }
        for (int i = 0; i < strs.length; i++) {
            list.add(strs[i]);
        }
        eduGradeDialog(view);
    }

    /**
     * @param strs      对比值
     * @param strsValue 返回值
     * @param textValue 获取值
     * @return 返回字符串
     */
    public String selectValue(String[] strs, String[] strsValue, String textValue) {
        for (int i = 0; i < strs.length; i++) {
            if (textValue.equals(strs[i])) {
                return strsValue[i];
            }
        }
        return "";
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
            tvContactAddressValue.setText(provinceName + cityName + districtName + areaName);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        LoadingCustom.showprogress(this, "", true);
        OkHttpClient okHttpClient = new OkHttpClient();
//        get 请求
        Request request = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.PERSONALINFO).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(4);
                Log.e("IdentifyInfoActivity", e.getMessage() + "失败");
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
                Log.e("IdentifyInfoActivity", respones);
            }
        });
    }

    /**
     * 修改用户信息接口
     *
     * @param fullName          全名
     * @param gender            性别
     * @param lastEducation     最高学历
     * @param maritalStatus     婚姻状态
     * @param childrenNumber    子女个数
     * @param address           详细地址
     * @param residenceDuration 居住时间
     * @param familyNameInLaw   母亲姓名
     */
    public void putInfo(String fullName, String gender, String lastEducation, String
            maritalStatus, String childrenNumber, String address, String residenceDuration,
                        String familyNameInLaw, String socialName, String socialId) {
        LoadingCustom.showprogress(this, "", true);
        OkHttpClient okHttpClient = new OkHttpClient();
//     ////        Post请求
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("fullName", fullName);
        builder.add("gender", gender);
        builder.add("lastEducation", lastEducation);
        builder.add("maritalStatus", maritalStatus);
        builder.add("childrenNumber", childrenNumber);
        builder.add("province", provinceName);
        builder.add("city", cityName);
        builder.add("district", districtName);
        builder.add("area", areaName);
        builder.add("address", address);
        builder.add("residenceDuration", residenceDuration);
        builder.add("familyNameInLaw", familyNameInLaw);
        builder.add("socialName", socialName);
        builder.add("facebookId", socialId);
        RequestBody formBody = builder.build();
        Request request1 = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.PERSONALINFO).put(formBody).build();
        Call call1 = okHttpClient.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(4);
                Log.e("IdentifyInfoActivity", e.getMessage() + "失败");
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
                Log.e("IdentifyInfoActivity", respones + "Post");
            }
        });
    }
}
