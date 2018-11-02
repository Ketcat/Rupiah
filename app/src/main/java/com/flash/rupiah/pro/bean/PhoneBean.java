package com.flash.rupiah.pro.bean;

/**
 * Created by heise on 2018/7/12.
 */

public class PhoneBean {
    String deviceType;
    String deviceBrand;
    String systemVersion;
    String versionName;
    String protocolVersion;

    public PhoneBean(String deviceType, String deviceBrand, String systemVersion, String
            versionName,  String protocolVersion) {
        this.deviceType = deviceType;
        this.deviceBrand = deviceBrand;
        this.systemVersion = systemVersion;
        this.versionName = versionName;
        this.protocolVersion = protocolVersion;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }
}
