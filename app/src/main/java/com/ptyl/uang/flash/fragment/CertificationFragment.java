package com.ptyl.uang.flash.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ptyl.uang.flash.R;
import com.ptyl.uang.flash.activity.ContactInfoActivity;
import com.ptyl.uang.flash.activity.IdentifyInfoActivity;
import com.ptyl.uang.flash.activity.IndustryInfoActivity;
import com.ptyl.uang.flash.activity.LoanActivity;
import com.ptyl.uang.flash.activity.PersonalActivity;
import com.ptyl.uang.flash.bean.CanLoanBean;
import com.ptyl.uang.flash.bean.ProgressBean;
import com.ptyl.uang.flash.utils.Contants;
import com.ptyl.uang.flash.utils.PhoneInfo;
import com.ptyl.uang.flash.utils.SharedPreUtil;
import com.google.gson.Gson;
import com.megvii.licensemanager.Manager;
import com.megvii.livenessdetection.LivenessLicenseManager;
import com.megvii.livenesslib.LivenessActivity;
import com.megvii.livenesslib.util.ConUtil;
import com.ptyl.uang.flash.view.LoadingCustom;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.os.Build.VERSION_CODES.M;

/**
 * 认证页面
 */
public class CertificationFragment extends BasicFragment implements View.OnClickListener {
    TextView tvPercent, tvOne, tvTwo, tvThree, tvFour, tvLineOne, tvLineTwo, tvLineThree,
            tvPersonalInfo, tvIdentifyInfo, tvContactInfo, tvIndustryInfo;
    ImageView ivPersonalInfo, ivIdentifyInfo, ivContactInfo, ivIndustryInfo;
    ProgressBar myProgressBar;
    TextView tvTitle;
    RelativeLayout rlPersonalInfo, rlIdentifyInfo, rlContactInfo, rlIndustryInfo;
    Button btNext;
    private MediaPlayer mMediaPlayer = null;
    public static final int EXTERNAL_STORAGE_REQ_CAMERA_CODE = 10;
    String uuid;
    String bestPath, envPath;
    String money, dates, token;
    private HashMap<String, String> map;//管理权限的map
    String[] permissions = new String[]{"android.permission.CAMERA", "android.permission" + "" +
            ".READ_PHONE_STATE"};
    boolean isPersonalInfoPart, isContactPart, isFilePart, isEmploymentPart;
    @SuppressLint("HandlerLeak")
    Handler handlerSkip = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.getData().getString("result");
            switch (msg.what) {
                case 1:
                    skipLoan(result);
                    break;
                case 5:
                    checkInfo(result);
                    break;
                case 3:
                    checkCanLoan(result);
                    break;
                case 4:
                    LoadingCustom.dismissprogress();
                    break;
            }

        }
    };

    public CertificationFragment() {
        // Required empty public constructor
    }

    public static CertificationFragment newInstance() {
        CertificationFragment fragment = new CertificationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(Contants.action);
        getActivity().registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_certification, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initListener() {
        btNext.setOnClickListener(this);
        rlPersonalInfo.setOnClickListener(this);
        rlIdentifyInfo.setOnClickListener(this);
        rlContactInfo.setOnClickListener(this);
        rlIndustryInfo.setOnClickListener(this);
    }

    private void initView(View view) {
        tvTitle = view.findViewById(R.id.tv_title);
        tvPercent = view.findViewById(R.id.data_percent);
        myProgressBar = view.findViewById(R.id.progressbar);
        tvOne = view.findViewById(R.id.tv_one);
        tvTwo = view.findViewById(R.id.tv_two);
        tvThree = view.findViewById(R.id.tv_three);
        tvFour = view.findViewById(R.id.tv_four);
        tvLineOne = view.findViewById(R.id.tv_line_one);
        tvLineTwo = view.findViewById(R.id.tv_line_two);
        tvLineThree = view.findViewById(R.id.tv_line_three);
        tvPersonalInfo = view.findViewById(R.id.tv_personal_info);
        tvIdentifyInfo = view.findViewById(R.id.tv_identify_info);
        tvContactInfo = view.findViewById(R.id.tv_contact_info);
        tvIndustryInfo = view.findViewById(R.id.tv_industry_info);
        ivPersonalInfo = view.findViewById(R.id.iv_personal_info);
        ivIdentifyInfo = view.findViewById(R.id.iv_identify_info);
        ivContactInfo = view.findViewById(R.id.iv_contact_info);
        ivIndustryInfo = view.findViewById(R.id.iv_industry_info);
        rlPersonalInfo = view.findViewById(R.id.rl_personal_info);
        rlIdentifyInfo = view.findViewById(R.id.rl_identify_info);
        rlContactInfo = view.findViewById(R.id.rl_contact_info);
        rlIndustryInfo = view.findViewById(R.id.rl_industry_info);
        btNext = view.findViewById(R.id.bt_certification_next);
    }

    private void initData() {
//        tvPercent.setText("35%");
        tvTitle.setText(R.string.certification);
        isLogin = SharedPreUtil.readBoolean(mContext, SharedPreUtil.ISLOGIN);
//        myProgressBar.setProgress(35);
        if (isLogin) {
            getPrograss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_personal_info:
                isLogin = SharedPreUtil.readBoolean(mContext, SharedPreUtil.ISLOGIN);
                if (isLogin) {
                    startActivity(new Intent(getActivity(), PersonalActivity.class));
                } else {
                    GoToMainActivity();
                }
                break;
            case R.id.rl_identify_info:
                isLogin = SharedPreUtil.readBoolean(mContext, SharedPreUtil.ISLOGIN);
                if (isLogin) {
                    startActivity(new Intent(getActivity(), IdentifyInfoActivity.class));
                } else {
                    GoToMainActivity();
                }
                break;
            case R.id.rl_contact_info:
                isLogin = SharedPreUtil.readBoolean(mContext, SharedPreUtil.ISLOGIN);
                if (isLogin) {
                    startActivity(new Intent(getActivity(), ContactInfoActivity.class));
                } else {
                    GoToMainActivity();
                }
                break;
            case R.id.rl_industry_info:
                isLogin = SharedPreUtil.readBoolean(mContext, SharedPreUtil.ISLOGIN);
                if (isLogin) {
                    startActivity(new Intent(getActivity(), IndustryInfoActivity.class));
                } else {
                    GoToMainActivity();
                }
                break;
            case R.id.bt_certification_next:
                qualification();
                break;
            default:
                break;
        }
    }

    private void createDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view_dialog = layoutInflater.inflate(R.layout.layout_identify_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.show();
        dialog.getWindow().setContentView(view_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btSure = view_dialog.findViewById(R.id.bt_face_sure);
        btSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                startActivity(new Intent(getContext(), FaceActivity.class));
                checkPermiss();
            }
        });
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            money = intent.getStringExtra("money");
            dates = intent.getStringExtra("date");
            getPrograss();
            if (intent.getBooleanExtra("isShow", false) == true) {
                btNext.setVisibility(View.VISIBLE);
            } else {
                btNext.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
        }
    }


    private void requestCameraPerm() {
        if (android.os.Build.VERSION.SDK_INT >= M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED) {
                //进行权限请求
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission
                        .CAMERA}, EXTERNAL_STORAGE_REQ_CAMERA_CODE);
            } else {
                enterNextPage();
            }
        } else {
            enterNextPage();
        }
    }

    private static final int PAGE_INTO_LIVENESS = 100;

    private void enterNextPage() {
        startActivityForResult(new Intent(getActivity(), LivenessActivity.class),
                PAGE_INTO_LIVENESS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAGE_INTO_LIVENESS && resultCode == Activity.RESULT_OK) {
//            String result = data.getStringExtra("result");
//            String delta = data.getStringExtra("delta");
//            Serializable images=data.getSerializableExtra("images");
            Bundle bundle = data.getExtras();
            checkIntent(bundle);
        }
    }


    /**
     * 联网授权
     */
    private void netWorkWarranty() {
        uuid = ConUtil.getUUIDString(getActivity());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Manager manager = new Manager(getActivity());
                LivenessLicenseManager licenseManager = new LivenessLicenseManager(getActivity());
                manager.registerLicenseManager(licenseManager);
                manager.takeLicenseFromNetwork(uuid);
                if (licenseManager.checkCachedLicense() > 0) {
                    //授权成功
                    mHandler.sendEmptyMessage(1);
                } else {
                    //授权失败
                    mHandler.sendEmptyMessage(2);
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    requestCameraPerm();
                    break;
                case 2:
                    Toast.makeText(getActivity(), R.string.network_fail, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    private void checkIntent(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        String resultOBJ = bundle.getString("result");
        try {
            JSONObject result = new JSONObject(resultOBJ);
            int resID = result.getInt("resultcode");
//            checkID(resID);
            boolean isSuccess = result.getString("result").equals(getResources().getString(R
                    .string.verify_success));

            if (isSuccess) {
                String delta = bundle.getString("delta");
                Map<String, byte[]> images = (Map<String, byte[]>) bundle.getSerializable("images");
                byte[] image_best = images.get("image_best");
                byte[] image_env = images.get("image_env");
                //N张动作图根据需求自取，对应字段action1、action2 ...
                // byte[] image_action1 = images.get("image_action1);
//                imageView.setImageBitmap(BitmapFactory.decodeByteArray(image_best, 0,
// image_best.length));

                //保存图片
                bestPath = ConUtil.saveJPGFile(getActivity(), image_best, "image_best");
                envPath = ConUtil.saveJPGFile(getActivity(), image_env, "image_env");
                File file = new File(bestPath);
                File file1 = new File(envPath);
                //调用活体比对API
//                imageVerify(images,delta);
                checkFace("PAYDAY", money.substring(3, money.indexOf(".")).replace(",", ""),
                        dates.replace("hari", ""), "D", file, file1, delta);
            }
//            doRotate(isSuccess);
//            startActivity(new Intent(getActivity(), LoanActivity.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkID(int resID) {
        if (resID == R.string.verify_success) {
            doPlay(R.raw.meglive_success);
        } else if (resID == R.string.liveness_detection_failed_not_video) {
            doPlay(R.raw.meglive_failed);
        } else if (resID == R.string.liveness_detection_failed_timeout) {
            doPlay(R.raw.meglive_failed);
        } else if (resID == R.string.liveness_detection_failed) {
            doPlay(R.raw.meglive_failed);
        } else {
            doPlay(R.raw.meglive_failed);
        }
    }

    private void doPlay(int rawId) {
        if (mMediaPlayer == null) mMediaPlayer = new MediaPlayer();
        mMediaPlayer.reset();
        try {
            AssetFileDescriptor localAssetFileDescriptor = getResources().openRawResourceFd(rawId);
            mMediaPlayer.setDataSource(localAssetFileDescriptor.getFileDescriptor(),
                    localAssetFileDescriptor.getStartOffset(), localAssetFileDescriptor.getLength
                            ());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception localIOException) {
            localIOException.printStackTrace();
        }
    }

    /**
     * @param loanType   贷款类型
     * @param amount     金额
     * @param period     时间
     * @param periodUnit 时间类型
     * @param imageBest  最好的图片
     * @param imageEnv
     * @param delta      人脸申请贷款
     */
    public synchronized void checkFace(String loanType, String amount, String period, String
            periodUnit, File imageBest, File imageEnv, String delta) {
        Intent intent = new Intent(Contants.action_running);
        intent.putExtra("running", true);
        getActivity().sendBroadcast(intent);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(3, TimeUnit
                .MINUTES).readTimeout(3, TimeUnit.MINUTES).build();
//     ////        Post请求
        HttpUrl url = HttpUrl.parse(Contants.FACE).newBuilder().addQueryParameter("imei",
                PhoneInfo.getIMEI(getActivity())).addQueryParameter("loanType", loanType).
                addQueryParameter("amount", amount).addQueryParameter("period", period)
                .addQueryParameter("periodUnit", periodUnit).addQueryParameter("delta", delta)
                .build();
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        final RequestBody body = RequestBody.create(MediaType.parse("image/*"), imageBest);
        RequestBody body1 = RequestBody.create(MediaType.parse("image/*"), imageEnv);
        requestBody.addFormDataPart("imageBest", imageBest.getName(), body);
        requestBody.addFormDataPart("imageEnv", imageEnv.getName(), body1);
        Log.e("checkFace",token);
        Request request = new Request.Builder().addHeader(Contants.HEADER_KEY, token).put
                (requestBody.build()).url(url).build();
        Call call1 = okHttpClient.newCall(request);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("CertificationFragment", "IOException" + e.getMessage());
                handlerSkip.sendEmptyMessage(4);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                Log.e("CertificationFragment", "result" + respones);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", respones);
                message.setData(bundle);
                message.what = 1;
                handlerSkip.sendMessage(message);
            }
        });
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
        switch (requestCode) {
            case Contants.REQUEST_PERMISSION_CODE_TAKE_PIC:
                boolean isAllGranted = true;
                for (int grant : grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        isAllGranted = false;
                        break;
                    }
                }
                if (!isAllGranted) {
                    Toast.makeText(getActivity(), R.string.permission_fail, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    netWorkWarranty();
                }
                break;
        }
    }

    /**
     * 请求权限
     */
    private void checkPermiss() {
        List<String> deniedPerms = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < permissions.length; i++) {
                if (PackageManager.PERMISSION_GRANTED != getContext().checkSelfPermission
                        (permissions[i])) {
                    deniedPerms.add(permissions[i]);
                }
            }
            int denyPermNum = deniedPerms.size();
            if (denyPermNum != 0) {
                requestPermissions(deniedPerms.toArray((new String[denyPermNum])), Contants
                        .REQUEST_PERMISSION_CODE_TAKE_PIC);
            } else {
                netWorkWarranty();
            }
        } else {
            netWorkWarranty();
        }
    }

    /**
     * 进度条进度
     */
    public void getPrograss() {
        token = SharedPreUtil.readString(mContext, SharedPreUtil.TOKEN, "");
        OkHttpClient okHttpClient = new OkHttpClient();
//        get 请求
        Request request = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.PROGRESS).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("CertificationFragment", e.getMessage() + "失败");
                handlerSkip.sendEmptyMessage(4);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", respones);
                message.setData(bundle);
                message.what = 5;
                handlerSkip.sendMessage(message);
                Log.e("CertificationFragment", respones + "getPrograss()");
            }
        });
    }

    private void skipLoan(String result) {
        String info;
        String message;
        if (!TextUtils.isEmpty(result)) {
            Gson gson = new Gson();
            HashMap<String, String> map = gson.fromJson(result, HashMap.class);
            info = map.get("data");
            if (!TextUtils.isEmpty(info)) {
                Intent intent = new Intent(Contants.action_running);
                intent.putExtra("running", false);
                getActivity().sendBroadcast(intent);
                Intent intentLoan = new Intent(getContext(), LoanActivity.class);
                intentLoan.putExtra("type", 1);
                intentLoan.putExtra("info", info);
                intentLoan.putExtra("money", money);
                intentLoan.putExtra("dates", dates);
                startActivity(intentLoan);
            } else {
                message = map.get("message");
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), R.string.face_fail, Toast.LENGTH_SHORT).show();
        }
        LoadingCustom.dismissprogress();
    }

    private void checkInfo(String result) {
        if (!TextUtils.isEmpty(result)) {
            Gson gson = new Gson();
            int index = 0;
            try {
                ProgressBean progressBean = gson.fromJson(result, ProgressBean.class);
                if (TextUtils.isEmpty(progressBean.getError())) {
                    isContactPart = progressBean.isContactPart();
                    isPersonalInfoPart = progressBean.isPersonalInfoPart();
                    isFilePart = progressBean.isFilePart();
                    isEmploymentPart = progressBean.isEmploymentPart();
                    if (progressBean.isPersonalInfoPart()) index = index + 1;
                    if (progressBean.isContactPart()) index = index + 1;
                    if (progressBean.isFilePart()) index = index + 1;
                    if (progressBean.isEmploymentPart()) index = index + 1;
                    tvPercent.setText(index * 100 / 4 + "%");
                    myProgressBar.setProgress(index * 100 / 4);
                    if (!isPersonalInfoPart && !isFilePart && !isContactPart && !isEmploymentPart) {
                        rlPersonalInfo.setEnabled(true);
                        rlContactInfo.setEnabled(false);
                        rlIdentifyInfo.setEnabled(true);
                        rlIndustryInfo.setEnabled(false);
                        ivIndustryInfo.setImageResource(R.mipmap.industry_info_no);
                        ivPersonalInfo.setImageResource(R.mipmap.personal_info_no);
                        ivContactInfo.setImageResource(R.mipmap.contact_info_no);
                        ivIdentifyInfo.setImageResource(R.mipmap.identify_info_no);
                        tvOne.setBackgroundDrawable((getActivity().getResources().getDrawable(R
                                .mipmap.circle_ing)));

                        tvTwo.setBackgroundDrawable((getActivity().getResources().getDrawable(R
                                .mipmap.circle_ing)));
                        tvThree.setBackgroundDrawable((getActivity().getResources().getDrawable(R
                                .mipmap.circle_ing)));
                        tvFour.setBackgroundDrawable((getActivity().getResources().getDrawable(R
                                .mipmap.circle_ing)));
                        tvLineOne.setBackgroundColor(getActivity().getResources().getColor(R
                                .color.circle_bg));
                        tvLineTwo.setBackgroundColor(getActivity().getResources().getColor(R
                                .color.circle_bg));
                        tvLineThree.setBackgroundColor(getActivity().getResources().getColor(R
                                .color.circle_bg));
                    } else if (isPersonalInfoPart && isFilePart && !isContactPart &&
                            !isEmploymentPart) {
                        rlPersonalInfo.setEnabled(true);
                        rlContactInfo.setEnabled(true);
                        rlIdentifyInfo.setEnabled(true);
                        rlIndustryInfo.setEnabled(false);
                        ivIndustryInfo.setImageResource(R.mipmap.industry_info_no);
                        ivPersonalInfo.setImageResource(R.mipmap.personal_info_yes);
                        ivContactInfo.setImageResource(R.mipmap.contact_info_no);
                        ivIdentifyInfo.setImageResource(R.mipmap.identify_info_yes);
                        tvTwo.setText("");
                        tvOne.setText("");
                        tvOne.setBackgroundDrawable((getActivity().getResources().getDrawable(R
                                .mipmap.circle_ed)));
                        tvLineOne.setBackgroundColor(getActivity().getResources().getColor(R
                                .color.green));
                        tvLineTwo.setBackgroundColor(getActivity().getResources().getColor(R
                                .color.circle_bg));
                        tvLineThree.setBackgroundColor(getActivity().getResources().getColor(R
                                .color.circle_bg));
                        tvTwo.setBackgroundDrawable((getActivity().getResources().getDrawable(R
                                .mipmap.circle_ed)));
                        tvThree.setBackgroundDrawable((getActivity().getResources().getDrawable(R
                                .mipmap.circle_ing)));
                        tvFour.setBackgroundDrawable((getActivity().getResources().getDrawable(R
                                .mipmap.circle_ing)));
                    } else if (isPersonalInfoPart && isFilePart && isContactPart &&
                            !isEmploymentPart) {
                        rlPersonalInfo.setEnabled(true);
                        rlContactInfo.setEnabled(true);
                        rlIdentifyInfo.setEnabled(true);
                        rlIndustryInfo.setEnabled(true);
                        ivIndustryInfo.setImageResource(R.mipmap.industry_info_no);
                        ivPersonalInfo.setImageResource(R.mipmap.personal_info_yes);
                        ivContactInfo.setImageResource(R.mipmap.contact_info_yes);
                        ivIdentifyInfo.setImageResource(R.mipmap.identify_info_yes);
                        tvTwo.setText("");
                        tvOne.setText("");
                        tvThree.setText("");
                        tvOne.setBackgroundDrawable((getActivity().getResources().getDrawable(R
                                .mipmap.circle_ed)));
                        tvTwo.setBackgroundDrawable((getActivity().getResources().getDrawable(R
                                .mipmap.circle_ed)));
                        tvThree.setBackgroundDrawable((getActivity().getResources().getDrawable(R
                                .mipmap.circle_ed)));
                        tvFour.setBackgroundDrawable((getActivity().getResources().getDrawable(R
                                .mipmap.circle_ing)));
                        tvLineOne.setBackgroundColor(getActivity().getResources().getColor(R
                                .color.green));
                        tvLineTwo.setBackgroundColor(getActivity().getResources().getColor(R
                                .color.green));
                        tvLineThree.setBackgroundColor(getActivity().getResources().getColor(R
                                .color.circle_bg));
                    } else if (isPersonalInfoPart && isFilePart && isContactPart &&
                            isEmploymentPart) {
                        rlPersonalInfo.setEnabled(true);
                        rlContactInfo.setEnabled(true);
                        rlIdentifyInfo.setEnabled(true);
                        rlIndustryInfo.setEnabled(true);
                        ivIndustryInfo.setImageResource(R.mipmap.industry_info_yes);
                        ivPersonalInfo.setImageResource(R.mipmap.personal_info_yes);
                        ivContactInfo.setImageResource(R.mipmap.contact_info_yes);
                        ivIdentifyInfo.setImageResource(R.mipmap.identify_info_yes);
                        tvTwo.setText("");
                        tvOne.setText("");
                        tvThree.setText("");
                        tvFour.setText("");
                        tvOne.setBackgroundDrawable((getActivity().getResources().getDrawable(R
                                .mipmap.circle_ed)));
                        tvTwo.setBackgroundDrawable((getActivity().getResources().getDrawable(R
                                .mipmap.circle_ed)));
                        tvThree.setBackgroundDrawable((getActivity().getResources().getDrawable(R
                                .mipmap.circle_ed)));
                        tvFour.setBackgroundDrawable((getActivity().getResources().getDrawable(R
                                .mipmap.circle_ed)));
                        tvLineOne.setBackgroundColor(getActivity().getResources().getColor(R
                                .color.green));
                        tvLineTwo.setBackgroundColor(getActivity().getResources().getColor(R
                                .color.green));
                        tvLineThree.setBackgroundColor(getActivity().getResources().getColor(R
                                .color.green));
                    }
                } else {
                    noDate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            noDate();
        }
        LoadingCustom.dismissprogress();
    }

    private void noDate() {
        tvPercent.setText("0%");
        myProgressBar.setProgress(0);
        tvOne.setText("1");
        tvTwo.setText("2");
        tvThree.setText("3");
        tvFour.setText("4");
        rlPersonalInfo.setEnabled(true);
        rlContactInfo.setEnabled(false);
        rlIdentifyInfo.setEnabled(true);
        rlIndustryInfo.setEnabled(false);
        ivIndustryInfo.setImageResource(R.mipmap.industry_info_no);
        ivPersonalInfo.setImageResource(R.mipmap.personal_info_no);
        ivContactInfo.setImageResource(R.mipmap.contact_info_no);
        ivIdentifyInfo.setImageResource(R.mipmap.identify_info_no);
        tvLineOne.setBackgroundColor(getActivity().getResources().getColor(R.color.circle_bg));
        tvLineTwo.setBackgroundColor(getActivity().getResources().getColor(R.color.circle_bg));
        tvLineThree.setBackgroundColor(getActivity().getResources().getColor(R.color.circle_bg));
        tvOne.setBackgroundDrawable((getActivity().getResources().getDrawable(R.mipmap
                .circle_ing)));
        tvTwo.setBackgroundDrawable((getActivity().getResources().getDrawable(R.mipmap
                .circle_ing)));
        tvThree.setBackgroundDrawable((getActivity().getResources().getDrawable(R.mipmap
                .circle_ing)));
        tvFour.setBackgroundDrawable((getActivity().getResources().getDrawable(R.mipmap
                .circle_ing)));
    }

    /**
     * 贷款申请资格
     */
    public void qualification() {
        LoadingCustom.showprogress(getActivity(), "", false);
        OkHttpClient okHttpClient = new OkHttpClient();
//        get 请求
        Request request = new Request.Builder().addHeader(Contants.HEADER_KEY, token).url
                (Contants.QUALIFICATION).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handlerSkip.sendEmptyMessage(4);
                Log.e("CertificationFragment", e.getMessage() + "失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respones = response.body().string();
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", respones);
                message.setData(bundle);
                message.what = 3;
                handlerSkip.sendMessage(message);
                Log.e("CertificationFragment", respones + "qualification()");
            }
        });
    }

    private void checkCanLoan(String result) {
        if (!TextUtils.isEmpty(result)) {
            try {
                Gson gson = new Gson();
                CanLoanBean errorBean = gson.fromJson(result, CanLoanBean.class);
                if (!TextUtils.isEmpty(errorBean.getError())) {
                    Toast.makeText(getActivity(), errorBean.getMessage().toString(), Toast
                            .LENGTH_SHORT).show();
                } else {
                    checkProgressInfo(errorBean.isLoan_qualification());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LoadingCustom.dismissprogress();
    }

    private void checkProgressInfo(boolean isLoan) {
        if (!isPersonalInfoPart) {
            startActivity(new Intent(getActivity(), PersonalActivity.class));
        } else if (!isContactPart) {
            startActivity(new Intent(getActivity(), ContactInfoActivity.class));
        } else if (!isFilePart) {
            startActivity(new Intent(getActivity(), PersonalActivity.class));
        } else if (!isEmploymentPart) {
            startActivity(new Intent(getActivity(), IndustryInfoActivity.class));
        } else if (isLoan) {
            createDialog();
        }
    }
}
