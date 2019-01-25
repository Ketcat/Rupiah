package com.rupiah.flash.pros.activity;

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
import com.rupiah.flash.pros.R;
import com.rupiah.flash.pros.adapter.DialogListAdapter;
import com.rupiah.flash.pros.bean.ErrorBean;
import com.rupiah.flash.pros.bean.UrlBean;
import com.rupiah.flash.pros.bean.UserInfoBean;
import com.rupiah.flash.pros.utils.Contants;
import com.rupiah.flash.pros.utils.SharedPreUtil;
import com.rupiah.flash.pros.utils.StatusBarUtils;
import com.rupiah.flash.pros.view.LoadingCustom;
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

public class PersonalActivity extends BasicActivity implements View.OnClickListener {
    TextView tvLine1, tvLine2, tvLine3, tvTitle, tvSex;
    ImageView ivInfo1, ivInfo2, ivInfo3, ivInfo4, ivBack, ivAddIdentify, ivAddFamily;
    RelativeLayout rlSexCheck;
    EditText etAllName, etIdentifyCode;
    Button btSendInfo;
    List<String> list;
    Intent intent;
    String token;
    String familyPhontoUrl, identifyPhotoUrl;
    String[] permissions = new String[]{"android.permission.CAMERA", "android.permission" + "" +
            ".WRITE_EXTERNAL_STORAGE"};
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
                            UrlBean urlBean = gson.fromJson(result, UrlBean.class);
                            if (urlBean.getFiles().size() > 0) {
                                identifyPhotoUrl = urlBean.getFiles().get(0).getUrl();
                                Glide.with(PersonalActivity.this).load(identifyPhotoUrl).error(R
                                        .mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher)
                                        .into(ivAddIdentify);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        if (!TextUtils.isEmpty(result)) {
                            userInfoBean = gson.fromJson(result, UserInfoBean.class);
                            etAllName.setText(userInfoBean.getFullName());
                            etIdentifyCode.setText(userInfoBean.getCredentialNo());
                            tvSex.setText("FEMALE".equals(userInfoBean.getGender()) ? "Perempuan"
                                    : "Laki Laki");
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
                            startActivity(new Intent(PersonalActivity.this, IdentifyInfoActivity
                                    .class));
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
                                familyPhontoUrl = urlBean.getFiles().get(0).getUrl();
                                Glide.with(PersonalActivity.this).load(familyPhontoUrl).error(R
                                        .mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher)
                                        .into(ivAddFamily);
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
        return R.layout.activity_personal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        StatusBarUtils.setWindowStatusBarColor(PersonalActivity.this, R.color.tab_bg,
                StatusBarUtils.Position.STATUS);
    }

    @Override
    protected void initView() {
        tvTitle = findViewById(R.id.tv_title);
        tvLine1 = findViewById(R.id.tv_line1);
        tvLine2 = findViewById(R.id.tv_line2);
        tvLine3 = findViewById(R.id.tv_line3);
        ivBack = findViewById(R.id.iv_title_back);
        ivInfo1 = findViewById(R.id.iv_info1);
        ivInfo2 = findViewById(R.id.iv_info2);
        ivInfo3 = findViewById(R.id.iv_info3);
        ivInfo4 = findViewById(R.id.iv_info4);
        tvSex = findViewById(R.id.tv_sex_check);
        ivAddIdentify = findViewById(R.id.iv_add_identify);
        ivAddFamily = findViewById(R.id.iv_add_family);
        rlSexCheck = findViewById(R.id.rl_check_sex);
        etAllName = findViewById(R.id.et_all_name);
        etIdentifyCode = findViewById(R.id.et_identify_number);
        btSendInfo = findViewById(R.id.bt_send_identify);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.personal_info);
        token = SharedPreUtil.readString(this, SharedPreUtil.TOKEN, "");
        ivBack.setVisibility(View.VISIBLE);
        intent = new Intent(PersonalActivity.this, MyCameraActivity.class);
        getUserInfo();
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        ivAddIdentify.setOnClickListener(this);
        ivAddFamily.setOnClickListener(this);
        rlSexCheck.setOnClickListener(this);
        btSendInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.iv_add_identify:
                addIdentifyDialog();
                break;
            case R.id.rl_check_sex:
                checkSexDialog();
                break;
            case R.id.bt_send_identify:
                String fullName = etAllName.getText().toString().trim();
                String id = etIdentifyCode.getText().toString().trim();
                String sex = ("Perempuan".equals(tvSex.getText().toString().trim()) ? "FEMALE" :
                        "MALE");
                if (!TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(id) && !TextUtils.isEmpty
                        (sex) && !TextUtils.isEmpty(familyPhontoUrl) && !TextUtils.isEmpty
                        (identifyPhotoUrl)) {
                    putInfo(fullName, sex, id);
                } else {
                    Toast.makeText(mContext, R.string.no_all_info, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_add_family:
                intent.putExtra("type", 2);
                checkPermiss();
                break;
            default:
                break;
        }
    }

    private void checkSexDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view_dialog = layoutInflater.inflate(R.layout.layout_list_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setContentView(view_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        list = new ArrayList<>();
        list.add(getResources().getString(R.string.male));
        list.add(getResources().getString(R.string.female));
        ListView listView = view_dialog.findViewById(R.id.dialog_list);
        DialogListAdapter adapter = new DialogListAdapter(list, mContext);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                tvSex.setText(list.get(position));
            }
        });
    }

    private void addIdentifyDialog() {
        intent.putExtra("type", 0);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view_dialog = layoutInflater.inflate(R.layout.layout_standard_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setContentView(view_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button button = view_dialog.findViewById(R.id.bt_standard_sure);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                checkPermiss();
            }
        });
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
            //请求图片下载
            getPhotoUrl();
            getFamily();
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == 1 && data != null) {
//            String path = data.getStringExtra("path");
//            try {
//                FileInputStream fs = new FileInputStream(path);
//                Bitmap bitmap = BitmapFactory.decodeStream(fs);
//                ivAddIdentify.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    /**
     * 检测权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[]
            grantResults) {
        switch (requestCode) {
            case Contants.REQUEST_PERMISSION_CODE_TAKE_PIC:
                checkPermiss();
                break;
        }
    }

    /**
     * 获取用户证件地址
     */
    public void getPhotoUrl() {
        OkHttpClient okHttpClient = new OkHttpClient();
//        get 请求
        Request request = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.KTP_PHOTO).build();
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
                message.what = 1;
                handler.sendMessage(message);
                Log.e("PersonalActivity", respones);
            }
        });
    }

    /**
     * 获取用户证件地址
     */
    public void getFamily() {
        OkHttpClient okHttpClient = new OkHttpClient();
//        get 请求
        Request request = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.GET_OTHER_PHOTO + "?type=FAMILY_CARD_PHOTO").build();
//        Request request = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
//                (Contants.GET_OTHER_PHOTO+"?type=OTHER_PHOTO").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("PersonalActivity", e.getMessage());
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
                Log.e("PersonalActivity", respones);
            }
        });
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        OkHttpClient okHttpClient = new OkHttpClient();
//        get 请求
        Request request = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.PERSONALINFO).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(4);
                Log.e("PersonalActivity", e.getMessage() + "失败");
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
                Log.e("PersonalActivity", respones);
            }
        });
    }

    /**
     * 修改用户信息接口
     *
     * @param fullName 全名
     * @param gender   性别
     * @param id       证件号码
     */
    public void putInfo(String fullName, String gender, String id) {
        LoadingCustom.showprogress(this, "", true);
        OkHttpClient okHttpClient = new OkHttpClient();
//     ////        Post请求
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("fullName", fullName);
        builder.add("gender", gender);
        builder.add("credentialNo", id);
        RequestBody formBody = builder.build();
        Request request1 = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.PERSONALINFO).put(formBody).build();
        Call call1 = okHttpClient.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(4);
                Log.e("PersonalActivity", e.getMessage() + "失败");
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
                Log.e("PersonalActivity", respones + "Post");
            }
        });
    }
}
