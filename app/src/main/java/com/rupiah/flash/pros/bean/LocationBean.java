package com.rupiah.flash.pros.bean;

import java.util.List;

/**
 * Created by heise on 2018/7/13.
 */

public class LocationBean {

    /**
     * data : [{"altitude":15.071534156799316,"latitude":31.240054393892397,
     * "longitude":121.67802162067197,"createTime":1512137051600}]
     * latestTime : 1512296132086
     * totalNumber : 1
     * versionName : 1.2.2
     * earliestTime : 0
     * protocolName : LOCATION
     * protocolVersion : V_1_0
     */

    private long latestTime;
    private int totalNumber;
    private String versionName;
    private int earliestTime;
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

    public int getEarliestTime() {
        return earliestTime;
    }

    public void setEarliestTime(int earliestTime) {
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
         * altitude : 15.071534156799316
         * latitude : 31.240054393892397
         * longitude : 121.67802162067197
         * createTime : 1512137051600
         */

        private double altitude;
        private double latitude;
        private double longitude;
        private long createTime;

        public double getAltitude() {
            return altitude;
        }

        public void setAltitude(double altitude) {
            this.altitude = altitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }
}
