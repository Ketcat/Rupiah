package com.rupiah.flash.pros.bean;

import java.util.List;

/**
 * Created by heise on 2018/7/12.
 */

public class ContactUser {

    /**
     * data : [{"name":"小杨","number":"18019480371","duration":"7","direction":"OUTGOING_TYPE",
     * "createTime":"1512223900277"}]
     * latestTime : 1512291480288
     * totalNumber : 1338
     * versionName : 1.2.2
     * earliestTime : UNKNOW
     * protocolName : CALL_LOG
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
         * name : 小杨
         * number : 18019480371
         * duration : 7
         * direction : OUTGOING_TYPE
         * createTime : 1512223900277
         */

        private String name;
        private String number;
        private String duration;
        private String direction;
        private String createTime;

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

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
