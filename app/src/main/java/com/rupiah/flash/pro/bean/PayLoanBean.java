package com.rupiah.flash.pro.bean;

/**
 * Created by heise on 2018/6/12.
 */

public class PayLoanBean {

    /**
     * depositId : 100012
     * price : 100000
     * currency : IDR
     * productId : 1606
     * operatorId : 6
     * depositChannel : BLUEPAY
     * depositMethod : OTHERS
     * paymentCode : 8888859300757380
     * status : null
     * msisidn : null
     * bankType : BNI
     * payType : OTC
     */

    private int depositId;
    private int price;
    private String currency;
    private String productId;
    private String operatorId;
    private String depositChannel;
    private String depositMethod;
    private String paymentCode;
    private String status;
    private String msisidn;
    private String bankType;
    private String payType;

    public int getDepositId() {
        return depositId;
    }

    public void setDepositId(int depositId) {
        this.depositId = depositId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getDepositChannel() {
        return depositChannel;
    }

    public void setDepositChannel(String depositChannel) {
        this.depositChannel = depositChannel;
    }

    public String getDepositMethod() {
        return depositMethod;
    }

    public void setDepositMethod(String depositMethod) {
        this.depositMethod = depositMethod;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsisidn() {
        return msisidn;
    }

    public void setMsisidn(String msisidn) {
        this.msisidn = msisidn;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
