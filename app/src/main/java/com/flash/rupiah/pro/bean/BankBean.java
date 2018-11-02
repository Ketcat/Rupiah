package com.flash.rupiah.pro.bean;

/**
 * Created by heise on 2018/6/9.
 */

public class BankBean {

    /**
     * bankPaymentId : 1
     * bankId : 144
     * bankName : Bank Central Asia (BCA)
     * bankCode : BCA
     */

    private int bankPaymentId;
    private int bankId;
    private String bankName;
    private String bankCode;

    public int getBankPaymentId() {
        return bankPaymentId;
    }

    public void setBankPaymentId(int bankPaymentId) {
        this.bankPaymentId = bankPaymentId;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
