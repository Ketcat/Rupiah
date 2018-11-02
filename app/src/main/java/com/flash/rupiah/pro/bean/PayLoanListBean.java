package com.flash.rupiah.pro.bean;

/**
 * Created by heise on 2018/6/13.
 */

public class PayLoanListBean {

    /**
     * id : 100000
     * status : CREATED
     * depositAmount : 10000.0
     * currency : IDR
     * payType : null
     * msisdn : null
     * bankType : null
     * paymentCode : null
     */

    private int id;
    private String status;
    private double depositAmount;
    private String currency;
    private String payType;
    private String msisdn;
    private String bankType;
    private String paymentCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }
}
