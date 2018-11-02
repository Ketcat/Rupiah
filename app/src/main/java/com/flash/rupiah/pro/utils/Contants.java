package com.flash.rupiah.pro.utils;

/**
 * Created by heise on 2018/5/16.
 */

public class Contants {
    public static final String phoneNum = "0895404990407";
    public static final String action = "com.example.rupiah";
    public static final String action_mine = "com.example.rupiah.mine";
    public static final String action_loan = "com.example.cash.rupiah";
    public static final String action_running = "com.example.cash.main";
    public static final int REQUEST_PERMISSION_CODE_TAKE_PIC = 1; //权限的请求码
    public static final int REQUEST_PERMISSION_SEETING = 8; //去设置界面的请求码
    public static final String HEADER_KEY = "X-AUTH-TOKEN"; //token
    public static final String TOKEN = "token"; //toke
    public static final String BASIC_URL = "http://149.129.214.206:8888";//正式环境
//        public static final String BASIC_URL = "http://192.168.1.107:8888";
//    public static final String BASIC_URL = "http://192.168.1.134:8888";
    public static final String LOGIN_ADDRESS = BASIC_URL + "/auth/login"; //登陆
    public static final String LOGOUT = BASIC_URL + "/auth/logout"; //登出
    public static final String PROGRESS = BASIC_URL + "/record/progress"; //获取用户档案完善进度get
    public static final String KTP_PHOTO = BASIC_URL + "/record/ktp-photo"; //获取用户身份证（KTP）图片信息get
    public static final String OCR = BASIC_URL + "/record/ocr"; //用户OCR认证put
    public static final String FILES = BASIC_URL + "/record/files"; //用户文件上传接口put
    public static final String PERSONALINFO = BASIC_URL + "/record/personalinfo";
    //修改用户个人信息认证put/获取用户个人信息认证get
    public static final String CONTACT = BASIC_URL + "/record/contact"; //修改用户联系人信息put/获取用户联系人信息get
    public static final String GET_EMPLOY_PHOTO = BASIC_URL + "/record/employ-photo";
    public static final String GET_OTHER_PHOTO = BASIC_URL + "/record/other-photo";//根据类型获取用户照片列表
    //获取用户工作照片列表get
    public static final String EMPLOYMENT = BASIC_URL + "/record/employment";
    //修改用户工作信息put/获取用户工作信息get
    public static final String ALL = BASIC_URL + "/loanapp/all"; //获取用户所有借款列表get
    public static final String LATEST = BASIC_URL + "/loanapp/latest"; //获取用户最近一笔借款信息get
    public static final String FACE = BASIC_URL + "/loanapp/verify/face"; //人脸识别借款——借款申请put
    public static final String LOANAPP = BASIC_URL + "/loanapp/"; //借款申请post
    public static final String RANGE = BASIC_URL + "/loanapp/range"; //查询借款范围get
    public static final String GETBANKCODE = BASIC_URL + "/loanapp/getBankCode"; //获取银行代码列表get
    public static final String CANCEL = BASIC_URL + "/loanapp/cancel"; //用户取消借款post
    public static final String DEPOSIT = BASIC_URL + "/loanapp/deposit"; //用户申请还款post
    public static final String AREA = BASIC_URL + "/region/area"; //地域街道地址
    public static final String CITY = BASIC_URL + "/region/city"; //城市地址
    public static final String DISTRICT = BASIC_URL + "/region/district"; //县级地址
    public static final String PROVINCE = BASIC_URL + "/region/province/0"; //省级地址
    public static final String DEPOSITLIST = BASIC_URL + "/loanapp/depositList"; //获取借款所有还款列表
    public static final String QUALIFICATION = BASIC_URL + "/loanapp/qualification"; //用户申请借款资格查询
    public static final String ABOUTUS = BASIC_URL + "/aboutUs.html";//  关于我们
    public static final String BORROWINGROUTE = BASIC_URL + "/borrowingRoute.html";//  如何借款
    public static final String DETAILED = BASIC_URL + "/detailed.html";//  隐私政策详情
    public static final String HELPPAGE = BASIC_URL + "/helpPage.html";//  帮助中心
    public static final String LOANCONTRACT = BASIC_URL + "/LoanContract.html";//  借款合同
    public static final String PRIVACYPOLICY = BASIC_URL + "/privacyPolicy.html";//  隐私政策
    public static final String REPAYMENT = BASIC_URL + "/repayment.html";//  还款途径
    public static final String VERSION = BASIC_URL + "/version/latest";//  获取移动端版本号
    public static final String INFOCOLLECTION = BASIC_URL + "/trace/infoCollection";//  移动设备数据采集
    public static final String UPDATEURL = "https://play.google.com/store/apps/details?id=com.ptyl.uang.flash";// 更新下载地址
    public static final String INTERGRAL =BASIC_URL+ "/record/integral";// 获取积分
}
