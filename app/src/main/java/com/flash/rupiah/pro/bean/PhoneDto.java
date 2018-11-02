package com.flash.rupiah.pro.bean;

import java.util.List;

/**
 * Created by heise on 2018/5/18.
 * 联系人信息bean
 */

public class PhoneDto {

    /**
     * data : [{"name":"孙玉琴","number":[{"number":"13697857528","time_used":1,"type_label":"住宅",
     * "last_time_used":"1504319011602"}],"lastUpdate":"1504319011602","contact_times":1,
     * "last_contact_time":"1504319011602"},{"name":"大神","number":[{"number":"15939055461",
     * "time_used":2,"type_label":"手机","last_time_used":"1507779331957"}],"relation":"2",
     * "lastUpdate":"1507779331957","contact_times":2,"last_contact_time":"1507779331957"},
     * {"name":"龙威","number":[{"number":"15864051806","time_used":0,"type_label":"手机"}],
     * "relation":"2","lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},
     * {"name":"大姐","number":[{"number":"15376121902","time_used":0,"type_label":"手机"}],
     * "relation":"2","lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},
     * {"name":"马快","number":[{"number":"15972971262","time_used":0,"type_label":"手机"}],
     * "relation":"2","lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},
     * {"name":"萍","number":[{"number":"15993900452","time_used":0,"type_label":"手机"}],
     * "relation":"2","lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},
     * {"name":"教授","number":[{"number":"15618655165","time_used":0,"type_label":"手机"}],
     * "relation":"2","lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},
     * {"name":"超冉","number":[{"number":"13569364596","time_used":0,"type_label":"手机"}],
     * "relation":"2","lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},
     * {"name":"老索","number":[{"number":"15000719176","time_used":0,"type_label":"手机"}],
     * "relation":"2","lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},
     * {"name":"马杰","number":[{"number":"13683709330","time_used":0,"type_label":"手机"}],
     * "relation":"2","lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},
     * {"name":"刘磊","number":[{"number":"15000331965","time_used":0,"type_label":"手机"}],
     * "relation":"2","lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},
     * {"name":"弟弟","number":[{"number":"13781409453","time_used":0,"type_label":"手机"}],
     * "relation":"2","lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},
     * {"name":"老爸","number":[{"number":"18737060278","time_used":0,"type_label":"手机"}],
     * "relation":"2","lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},
     * {"name":"苏瑞琳","number":[{"number":"1752110","time_used":1,"type_label":"手机",
     * "last_time_used":"1512138177677"}],"relation":"2","lastUpdate":"1512138177677",
     * "contact_times":1,"last_contact_time":"1512138177677"},{"name":"二舅",
     * "number":[{"number":"15224977688","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},{"name":"刘娟",
     * "number":[{"number":"15990948126","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},{"name":"景沼",
     * "number":[{"number":"17756727677","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},{"name":"三姐",
     * "number":[{"number":"18337829678","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},{"name":"卖麻花",
     * "number":[{"number":"18937037837","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},{"name":"华伟",
     * "number":[{"number":"15837815757","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},{"name":"医院的",
     * "number":[{"number":"15026541156","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},{"name":"雷飞",
     * "number":[{"number":"13963878279","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},{"name":"二姐",
     * "number":[{"number":"15212852830","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},{"name":"小王",
     * "number":[{"number":"13182971328","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},{"name":"华伟",
     * "number":[{"number":"17317305456","time_used":3,"type_label":"手机",
     * "last_time_used":"1512186412656"}],"relation":"2","lastUpdate":"1512186412656",
     * "contact_times":3,"last_contact_time":"1512186412656"},{"name":"爸爸",
     * "number":[{"number":"18738072025","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},{"name":"权",
     * "number":[{"number":"15092948868","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},{"name":"龙威",
     * "number":[{"number":"18336936631","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},{"name":"卢自强",
     * "number":[{"number":"18653724595","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},{"name":"老贾",
     * "number":[{"number":"13863806119","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},{"name":"小杨",
     * "number":[{"number":"18019480371","time_used":9,"type_label":"手机",
     * "last_time_used":"1512223920765"}],"relation":"2","lastUpdate":"1512223920765",
     * "contact_times":9,"last_contact_time":"1512223920765"},{"name":"宫媛媛",
     * "number":[{"number":"18253511167","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},{"name":"妹妹",
     * "number":[{"number":"+8615137848237","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129645746","contact_times":0,"last_contact_time":"0"},{"name":"银山",
     * "number":[{"number":"18354586586","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"亚昆",
     * "number":[{"number":"13213168581","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"王世权",
     * "number":[{"number":"18396611338","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"王小波",
     * "number":[{"number":"13562521239","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"银山",
     * "number":[{"number":"17055468661","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"宗留洋",
     * "number":[{"number":"15737816168","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"姗姗",
     * "number":[{"number":"18903783586","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"苏东山",
     * "number":[{"number":"15938324674","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"赞赞",
     * "number":[{"number":"15064565025","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"永刚哥",
     * "number":[{"number":"15036661295","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"朱倩倩",
     * "number":[{"number":"15660924137","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"徐猛",
     * "number":[{"number":"15938533378","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"艳华",
     * "number":[{"number":"18736703809","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"苏佳佳",
     * "number":[{"number":"13598336512","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"赞赞妈",
     * "number":[{"number":"18272684012","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"徐赞",
     * "number":[{"number":"13791295605","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"昭仔",
     * "number":[{"number":"18133312677","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"王洪生",
     * "number":[{"number":"15065767029","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"孙琳",
     * "number":[{"number":"15064513000","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"苏州",
     * "number":[{"number":"18315450415","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"吴部队",
     * "number":[{"number":"13271591457","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"苏永刚",
     * "number":[{"number":"15266557810","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"扎卡",
     * "number":[{"number":"13616455705","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"苏红",
     * "number":[{"number":"15672839158","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"政权",
     * "number":[{"number":"18300543475","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"姨",
     * "number":[{"number":"13856727197","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"},{"name":"小松",
     * "number":[{"number":"18005301902","time_used":0,"type_label":"手机"}],"relation":"2",
     * "lastUpdate":"1512129647014","contact_times":0,"last_contact_time":"0"}]
     * latestTime : 1512292143737
     * totalNumber : 60
     * versionName : 1.2.2
     * earliestTime : 1512291458881
     * protocolName : CONTACT
     * protocolVersion : V_1_0
     */

    private long latestTime;
    private int totalNumber;
    private String versionName;
    private String earliestTime;
    private String protocolName;
    private String protocolVersion;
    private List<DataBean> data;

    public long getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(long latestTime) {
        this.latestTime = latestTime;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getEarliestTime() {
        return earliestTime;
    }

    public void setEarliestTime(String earliestTime) {
        this.earliestTime = earliestTime;
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
        /**
         * name : 孙玉琴
         * number : [{"number":"13697857528","time_used":1,"type_label":"住宅",
         * "last_time_used":"1504319011602"}]
         * lastUpdate : 1504319011602
         * contact_times : 1
         * last_contact_time : 1504319011602
         * relation : 2
         */

        private String name;
        private String lastUpdate;
        private int contact_times;
        private String last_contact_time;
        private String relation;
        private List<NumberBean> number;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(String lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public int getContact_times() {
            return contact_times;
        }

        public void setContact_times(int contact_times) {
            this.contact_times = contact_times;
        }

        public String getLast_contact_time() {
            return last_contact_time;
        }

        public void setLast_contact_time(String last_contact_time) {
            this.last_contact_time = last_contact_time;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }

        public List<NumberBean> getNumber() {
            return number;
        }

        public void setNumber(List<NumberBean> number) {
            this.number = number;
        }

        public static class NumberBean {
            /**
             * number : 13697857528
             * time_used : 1
             * type_label : 住宅
             * last_time_used : 1504319011602
             */

            private String number;
            private int time_used;
            private String type_label;
            private String last_time_used;

            public NumberBean(String number, int time_used, String type_label, String
                    last_time_used) {
                this.number = number;
                this.time_used = time_used;
                this.type_label = type_label;
                this.last_time_used = last_time_used;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public int getTime_used() {
                return time_used;
            }

            public void setTime_used(int time_used) {
                this.time_used = time_used;
            }

            public String getType_label() {
                return type_label;
            }

            public void setType_label(String type_label) {
                this.type_label = type_label;
            }

            public String getLast_time_used() {
                return last_time_used;
            }

            public void setLast_time_used(String last_time_used) {
                this.last_time_used = last_time_used;
            }
        }
    }
}
