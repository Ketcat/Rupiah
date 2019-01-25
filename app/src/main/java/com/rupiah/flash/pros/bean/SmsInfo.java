package com.rupiah.flash.pros.bean;

import java.util.List;

/**
 * Created by heise on 2018/5/18.
 */

public class SmsInfo {
    //    /**
//     * data : [{"name":"UNKNOW","number":"95533",
//     * "content":"您尾号1395的储蓄卡账户12月1日12时12分消费支出人民币17.00元,活期余额281.70元。[建设银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1512101580119},
//     * {"name":"UNKNOW","number":"10001",
//     * "content":"亲爱的客户，您本月获得赠送话费5.00元，该话费仅限当月使用，敬请留意，感谢您使用中国电信CDMA移动业务！[中国电信上海公司]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1512099038333},
//     * {"name":"UNKNOW","number":"10001","content":"尊敬的客户，您本月已获得赠款赠送金额70.00元，剩余赠款金额560.00元，感谢您的使用
//     * .[中国电信上海公司]","subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX",
//     * "createTime":1512096235159},{"name":"UNKNOW","number":"95533",
//     * "content":"您尾号1395的储蓄卡账户12月1日7时52分消费支出人民币99.90元,活期余额298.70元。[建设银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1512086101760},
//     * {"name":"UNKNOW","number":"95533","content":"您尾号1395的储蓄卡账户12月1日7时39分收费支出人民币2.00元,
//     * 活期余额398.60元。[建设银行]","subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX",
//     * "createTime":1512085193597},{"name":"UNKNOW","number":"95533",
//     * "content":"您尾号1395的储蓄卡账户12月1日7时39分跨行自助取款支出人民币100.00元,活期余额400.60元。[建设银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1512085186095},
//     * {"name":"UNKNOW","number":"95533","content":"您尾号1395的储蓄卡账户12月1日7时28分消费支出人民币5.00元,
//     * 活期余额500.60元。[建设银行]","subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX",
//     * "createTime":1512084540995},{"name":"UNKNOW","number":"95533",
//     * "content":"您尾号1395的储蓄卡账户11月30日18时53分招商银行其他费用支出人民币20.00元,活期余额505.60元。[建设银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1512039205313},
//     * {"name":"UNKNOW","number":"95533","content":"您尾号1395的储蓄卡账户11月30日12时27分消费支出人民币14.00元,
//     * 活期余额525.60元。[建设银行]","subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX",
//     * "createTime":1512016082708},{"name":"UNKNOW","number":"95533",
//     * "content":"您尾号1395的储蓄卡账户11月30日7时30分消费支出人民币15.00元,活期余额539.60元。[建设银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511998268942},
//     * {"name":"UNKNOW","number":"95533","content":"您尾号1395的储蓄卡账户11月29日17时52分消费支出人民币1.00元,
//     * 活期余额554.60元。[建设银行]","subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX",
//     * "createTime":1511949172416},{"name":"UNKNOW","number":"95533",
//     * "content":"您尾号1395的储蓄卡账户11月29日16时55分消费支出人民币1.00元,活期余额555.60元。[建设银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511945762690},
//     * {"name":"UNKNOW","number":"95533","content":"您尾号1395的储蓄卡账户11月29日16时1分消费支出人民币6.00元,
//     * 活期余额556.60元。[建设银行]","subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX",
//     * "createTime":1511942547741},{"name":"UNKNOW","number":"95533",
//     * "content":"您尾号1395的储蓄卡账户11月29日15时49分消费支出人民币5.00元,活期余额562.60元。[建设银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511941791678},
//     * {"name":"UNKNOW","number":"95555",
//     * "content":"[可视柜台短信回单]您账户4763已完成风险评估，风险承受能力级别A1，风险类型谨慎型，适合的产品类型谨慎型（R1）产品。如有疑问，请联系95555
//     * 。[招商银行]","subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX",
//     * "createTime":1511933287506},{"name":"UNKNOW","number":"95533",
//     * "content":"您尾号1395的储蓄卡账户11月29日13时23分招商银行其他费用支出人民币1000.00元,活期余额567.60元。[建设银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511933064286},
//     * {"name":"UNKNOW","number":"95555",
//     * "content":"您账户4763于11月29日发生的银联卡转入已到账，人民币1000.00，转出方尾号1395[招商银行]","subject":"NO_SUBJECT",
//     * "direction":"MESSAGE_TYPE_INBOX","createTime":1511933049290},{"name":"UNKNOW",
//     * "number":"95555","content":"转账验证码：076080。收款人：苏鹏飞。转出卡尾号：1395，金额1000元。请保密并确认本人操作！[招商银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511933013524},
//     * {"name":"UNKNOW","number":"95555",
//     * "content":"验证码167166，您正在用一网通登录，如非本人操作，请联系95555。请勿在任何短信或邮件链接的页面中输入验证码！ [招商银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511932962270},
//     * {"name":"UNKNOW","number":"95555",
//     * "content":"您已成功注册一网通，可享受招行优质服务。一网通支持多家银行卡，网上支付安全便捷。滴滴出行率先接入，欢迎体验，详询95555。[招商银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511932939342},
//     * {"name":"UNKNOW","number":"95555",
//     * "content":"一网通用户注册验证码：297275，请妥善保管，切勿泄露。如非本人操作，请登录招行主页向在线客服反馈。[招商银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511932795884},
//     * {"name":"UNKNOW","number":"95555",
//     * "content":"欢迎使用招行网银专业版。您证书KEY号为2712419051，请核对清楚并自行妥善保管。使用网银专业版请登录http://www.cmbchina
//     * .com下载安装专业版软件，详细演示流程请浏览e95555.cn/zjBE2A。[招商银行]","subject":"NO_SUBJECT",
//     * "direction":"MESSAGE_TYPE_INBOX","createTime":1511931846005},{"name":"UNKNOW",
//     * "number":"95555","content":"欢迎开立招商银行账户！您预留在我行的180****5679
//     * 手机号非常重要，是我行与您联系和交易确认的关键电话，如需变更请及时通过我行专业版或柜台修改。用卡指南详见http://cmbt.cn/vrn7nR[招商银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511930894013},
//     * {"name":"UNKNOW","number":"95555",
//     * "content":"尊敬的客户，您已在我行开立了首个银行账户（非贵宾账户），根据监管机构要求，我行已将您该账户设置为账户管理费的减免账户[招商银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511930888258},
//     * {"name":"UNKNOW","number":"95555",
//     * "content":"尊敬的用户，感谢您申办招商银行一卡通，更多优惠，免费转账，专属高收益理财请下载手机银行cmbt.cn/EQUAaB[招商银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511930880857},
//     * {"name":"UNKNOW","number":"95555",
//     * "content":"您尾号5679手机验证码是485596。该手机号码非常重要，是我行与您联系和交易确认关键电话，如变更可通过我行专业版或柜台修改。[招商银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511930653133},
//     * {"name":"UNKNOW","number":"18917568790","content":"身份证，银行卡，证书，户口本，原件？还是复印件？",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_SENT","createTime":1511929943146},
//     * {"name":"UNKNOW","number":"02163526880",
//     * "content":"尊敬的客户，感谢致电上海博为峰软件技术股份有限公司。我们将竭诚为您提供服务，欢迎来电垂询！【上海博为峰】","subject":"NO_SUBJECT",
//     * "direction":"MESSAGE_TYPE_INBOX","createTime":1511925675946},{"name":"UNKNOW",
//     * "number":"18917568790","content":"好，可以的","subject":"NO_SUBJECT",
//     * "direction":"MESSAGE_TYPE_INBOX","createTime":1511920353852},{"name":"UNKNOW",
//     * "number":"18917568790","content":"你好，我是苏鹏飞，我们今天面试可以放到上午11点吗","subject":"NO_SUBJECT",
//     * "direction":"MESSAGE_TYPE_SENT","createTime":1511920255476},{"name":"UNKNOW",
//     * "number":"95533","content":"您尾号1395的储蓄卡账户11月28日18时55分消费支出人民币59.50元,活期余额1567.60元。[建设银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511866586822},
//     * {"name":"UNKNOW","number":"13671633839",
//     * "content":"苏鹏飞，你好！我是汉克时代众安科技项目负责人郑飞；众安保险面试时间：明天下午2：00点；面试地址：黄浦区北京东路130号中实大楼2F；路线：2号线南京东路站6
//     * 号口出；快到打我电话，我来接你～不好意思，第一个短信发错，请忽略。","subject":"NO_SUBJECT",
//     * "direction":"MESSAGE_TYPE_INBOX","createTime":1511864868891},{"name":"UNKNOW",
//     * "number":"13671633839","content":"贾亚静，你好！我是汉克时代众安科技项目负责人郑飞；众安保险面试时间：明天下午2：00
//     * 点；面试地址：黄浦区北京东路130号中实大楼2F；路线：2号线南京东路站6号口出；快到打我电话，我来接你～","subject":"NO_SUBJECT",
//     * "direction":"MESSAGE_TYPE_INBOX","createTime":1511864828154},{"name":"UNKNOW",
//     * "number":"95533","content":"您尾号1395的储蓄卡账户11月28日17时59分向支付宝-张波消费支出人民币25.00元,
//     * 活期余额1627.10元。[建设银行]","subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX",
//     * "createTime":1511863209694},{"name":"UNKNOW","number":"95533",
//     * "content":"您尾号1395的储蓄卡账户11月28日17时55分消费支出人民币16.00元,活期余额1652.10元。[建设银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511862976719},
//     * {"name":"UNKNOW","number":"95533","content":"您尾号1395的储蓄卡账户11月28日17时48分消费支出人民币5.80元,
//     * 活期余额1668.10元。[建设银行]","subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX",
//     * "createTime":1511862551595},{"name":"UNKNOW","number":"95533",
//     * "content":"您尾号1395的储蓄卡账户11月28日17时46分消费支出人民币155.00元,活期余额1673.90元。[建设银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511862397975},
//     * {"name":"UNKNOW","number":"95533","content":"您尾号1395的储蓄卡账户11月28日17时26分跨行其他渠道消费支出人民币51.20元,
//     * 活期余额1828.90元。[建设银行]","subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX",
//     * "createTime":1511861635426},{"name":"UNKNOW","number":"13917486074","content":"好的",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_SENT","createTime":1511851239570},
//     * {"name":"UNKNOW","number":"13917486074",
//     * "content":"嗯嗯，这边您的入职在走审批流程，总裁这边今天签字了我会马上给您发offer的","subject":"NO_SUBJECT",
//     * "direction":"MESSAGE_TYPE_INBOX","createTime":1511851190359},{"name":"UNKNOW",
//     * "number":"95533","content":"您尾号1395的储蓄卡账户11月28日12时35分消费支出人民币200.00元,活期余额1880.10元。[建设银行]",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511843779545},
//     * {"name":"UNKNOW","number":"95533","content":"您尾号1395的储蓄卡账户11月28日11时50分消费支出人民币100.00元,
//     * 活期余额2080.10元。[建设银行]","subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX",
//     * "createTime":1511841035325},{"name":"UNKNOW","number":"15829792117","content":"合作哈",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511839898768},
//     * {"name":"UNKNOW","number":"15829792117",
//     * "content":"苏鹏飞你好，我是文思海辉的HR，刚有给你打电话，我们项目领导说，我们浦发银行的项目现在也要查学历，所以目前没有合适的岗位可以给你推荐，以后有机会",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511839895772},
//     * {"name":"UNKNOW","number":"95533","content":"您尾号1395的储蓄卡账户11月27日20时58分跨行其他渠道消费支出人民币92.10元,
//     * 活期余额2180.10元。[建设银行]","subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX",
//     * "createTime":1511787746509},{"name":"UNKNOW","number":"10659059106380023297",
//     * "content":"【哈罗单车】苏鹏飞，您好\n现通知您于2017年11月30日 星期四14:00，参加哈罗单车公司软件测试相关职位的面试\n面试地点：上海 闵行区
//     * 秀文路898号西子国际1栋5楼\n联系人：何妙珠\n电话：18701933946\n请准时到达","subject":"NO_SUBJECT",
//     * "direction":"MESSAGE_TYPE_INBOX","createTime":1511772327521},{"name":"UNKNOW",
//     * "number":"18601750395","content":"嗯","subject":"NO_SUBJECT",
//     * "direction":"MESSAGE_TYPE_SENT","createTime":1511753523176},{"name":"UNKNOW",
//     * "number":"18601750395","content":"好的，你自己把握","subject":"NO_SUBJECT",
//     * "direction":"MESSAGE_TYPE_INBOX","createTime":1511753510754},{"name":"UNKNOW",
//     * "number":"02150110265","content":"感谢您致电美味不用等（上海）信息科技股份有限公司，我们将竭诚为您服务，欢迎再次来电！【美味不用等】",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_INBOX","createTime":1511751832468},
//     * {"name":"UNKNOW","number":"18601750395","content":"有可能下午六点不去了，因为路程有点远，我回到家不知道几点了呢！",
//     * "subject":"NO_SUBJECT","direction":"MESSAGE_TYPE_SENT","createTime":1511751806947}]
//     * pageNo : 0
//     * pageSum : 4
//     * currentSum : 50
//     * updateTime : 1512113675973
//     * versionName : 1.2.2
//     * protocolName : SMS_LOG
//     * protocolVersion : V_1_0
//     */
//
    private int pageNo;
    private int pageSum;
    private int currentSum;
    private long updateTime;
    private String versionName;
    private String protocolName;
    private String protocolVersion;
    private List<DataBean> data;

    public SmsInfo(int pageNo, int pageSum, int currentSum, long updateTime, String versionName,
                   String protocolName, String protocolVersion, List<DataBean> data) {
        this.pageNo = pageNo;
        this.pageSum = pageSum;
        this.currentSum = currentSum;
        this.updateTime = updateTime;
        this.versionName = versionName;
        this.protocolName = protocolName;
        this.protocolVersion = protocolVersion;
        this.data = data;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSum() {
        return pageSum;
    }

    public void setPageSum(int pageSum) {
        this.pageSum = pageSum;
    }

    public int getCurrentSum() {
        return currentSum;
    }

    public void setCurrentSum(int currentSum) {
        this.currentSum = currentSum;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        public DataBean(String name, String number, String content, String subject, String
                direction, long createTime) {
            this.name = name;
            this.number = number;
            this.content = content;
            this.subject = subject;
            this.direction = direction;
            this.createTime = createTime;
        }

        /**
         * name : UNKNOW
         * number : 95533
         * content : 您尾号1395的储蓄卡账户12月1日12时12分消费支出人民币17.00元,活期余额281.70元。[建设银行]
         * subject : NO_SUBJECT
         * direction : MESSAGE_TYPE_INBOX
         * createTime : 1512101580119
         */

        private String name;
        private String number;
        private String content;
        private String subject;
        private String direction;
        private long createTime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }

}
