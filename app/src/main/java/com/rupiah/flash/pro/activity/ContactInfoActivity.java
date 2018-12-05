package com.rupiah.flash.pro.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rupiah.flash.pro.R;
import com.rupiah.flash.pro.bean.ContactBean;
import com.rupiah.flash.pro.bean.ErrorBean;
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

/**
 * 联系人信息界面
 */
public class ContactInfoActivity extends BasicActivity implements View.OnClickListener {
    TextView tvTitle;
    ImageView ivBack;
    TextView etParentsName, etParentsPhoneNumber, etBrotherName, etBrotherPhoneNumber,
            etSpouseName, etSpousePhoneNumber;
    Button btContactInfoSend;
    String token;
    ImageView ivBrother, ivFriend, ivSpouse;
    String[] permissions = new String[]{"android.permission.READ_SMS", "android.permission" + ""
            + ".READ_CONTACTS"};
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.getData().getString("result");
            Gson gson = new Gson();
            switch (msg.what) {
                case 1:
                    try {
                        ContactBean contactBean = gson.fromJson(result, ContactBean.class);
                        etParentsName.setText(contactBean.getParentName());
                        etParentsPhoneNumber.setText(contactBean.getParentMobile());
                        etBrotherName.setText(contactBean.getFriendName());
                        etBrotherPhoneNumber.setText(contactBean.getFriendMobile());
                        etSpouseName.setText(contactBean.getSpouseName());
                        etSpousePhoneNumber.setText(contactBean.getSpouseMobile());
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
                            startActivity(new Intent(ContactInfoActivity.this,
                                    IndustryInfoActivity.class));
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
        return R.layout.activity_contact_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        StatusBarUtils.setWindowStatusBarColor(ContactInfoActivity.this, R.color.tab_bg,
                StatusBarUtils.Position.STATUS);
    }

    @Override
    protected void initView() {
        tvTitle = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_title_back);
        ivBrother = findViewById(R.id.iv_contact_info_user_name);
        ivFriend = findViewById(R.id.iv_brother_contact_info_user_name);
        ivSpouse = findViewById(R.id.iv_spouse_contact_info_user_name);
        etParentsName = findViewById(R.id.et_parents_name);
        etParentsPhoneNumber = findViewById(R.id.et_parents_phone_number);
        etBrotherName = findViewById(R.id.et_brother_name);
        etBrotherPhoneNumber = findViewById(R.id.et_brother_phone_number);
        btContactInfoSend = findViewById(R.id.bt_contact_info_send);
        etSpouseName = findViewById(R.id.et_spouse_name);
        etSpousePhoneNumber = findViewById(R.id.et_spouse_phone_number);
        checkPermiss();
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.contact_info);
        token = SharedPreUtil.readString(this, SharedPreUtil.TOKEN, "");
        ivBack.setVisibility(View.VISIBLE);
        getContacts();
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        ivBrother.setOnClickListener(this);
        ivFriend.setOnClickListener(this);
        ivSpouse.setOnClickListener(this);
        btContactInfoSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.iv_brother_contact_info_user_name:
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts
                        .CONTENT_URI), 1);
                break;
            case R.id.iv_contact_info_user_name:
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts
                        .CONTENT_URI), 0);
                break;
            case R.id.iv_spouse_contact_info_user_name:
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts
                        .CONTENT_URI), 2);
                break;
            case R.id.bt_contact_info_send:
                String parentName = etParentsName.getText().toString().trim();
                String parentMobile = etParentsPhoneNumber.getText().toString().trim();
                String friendName = etBrotherName.getText().toString().trim();
                String friendMobile = etBrotherPhoneNumber.getText().toString().trim();
                String spouseName = etSpouseName.getText().toString().trim();
                String spouseMobile = etSpousePhoneNumber.getText().toString().trim();
                if (!TextUtils.isEmpty(parentName) && !TextUtils.isEmpty(parentMobile) &&
                        !TextUtils.isEmpty(friendName) && !TextUtils.isEmpty(friendMobile)) {
                    putContacts(parentName, parentMobile, friendName, friendMobile,spouseName,spouseMobile);
                } else {
                    Toast.makeText(mContext, R.string.no_all_info, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    public void getContacts() {
        LoadingCustom.showprogress(this, "", true);
        OkHttpClient okHttpClient = new OkHttpClient();
//        get 请求
        Request request = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.CONTACT).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LoadingCustom.dismissprogress();
                Log.e("ContactInfoActivity", e.getMessage() + "失败");
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
                LoadingCustom.dismissprogress();
                Log.e("ContactInfoActivity", respones);
            }
        });
    }

    public void putContacts(String parentName, String parentMobile, String friendName, String
            friendMobile,String spouseName,String spouseMobile) {
        LoadingCustom.showprogress(this, "", true);
        OkHttpClient okHttpClient = new OkHttpClient();
//     ////        Post请求
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("parentName", parentName);
        builder.add("parentMobile", parentMobile);
        builder.add("friendName", friendName);
        builder.add("friendMobile", friendMobile);
        builder.add("spouseName", spouseName);
        builder.add("spouseMobile", spouseMobile);
        RequestBody formBody = builder.build();
        Request request1 = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.CONTACT).put(formBody).build();
        Call call1 = okHttpClient.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LoadingCustom.dismissprogress();
                Log.e("ContactInfoActivity", e.getMessage() + "失败");
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
                LoadingCustom.dismissprogress();
                Log.e("ContactInfoActivity", respones + "Post");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ContentResolver reContentResolverol = getContentResolver();
            Uri contactData = data.getData();
            @SuppressWarnings("deprecation") Cursor cursor = managedQuery(contactData, null,
                    null, null, null);
            cursor.moveToFirst();
            String username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts
                    .DISPLAY_NAME));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts
                    ._ID));
            Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone
                    .CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                    + contactId, null, null);
            while (phone.moveToNext()) {
                String usernumber = phone.getString(phone.getColumnIndex(ContactsContract
                        .CommonDataKinds.Phone.NUMBER));
                if (requestCode == 1) {
                    etBrotherName.setText(username);
                    etBrotherPhoneNumber.setText(usernumber);
                } else if (requestCode == 0) {
                    etParentsName.setText(username);
                    etParentsPhoneNumber.setText(usernumber);
                } else if (requestCode == 2) {
                    etSpouseName.setText(username);
                    etSpousePhoneNumber.setText(usernumber);
                }
            }

        }
    }

    /**
     * 请求权限
     */
    private synchronized void checkPermiss() {
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
                ivBrother.setEnabled(true);
                ivFriend.setEnabled(true);
            }
        }
    }

    /**
     * 检测权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[]
            grantResults) {
        if (requestCode == Contants.REQUEST_PERMISSION_CODE_TAKE_PIC) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
//                    boolean showRequestPermission = ActivityCompat
//                            .shouldShowRequestPermissionRationale(ContactInfoActivity.this,
//                                    permissions[i]);
                    Toast.makeText(ContactInfoActivity.this, R.string.no_permission, Toast
                            .LENGTH_SHORT).show();
                    ivFriend.setEnabled(false);
                    ivBrother.setEnabled(false);
                } else {
                    ivBrother.setEnabled(true);
                    ivFriend.setEnabled(true);
                }
            }
        }
    }
}
