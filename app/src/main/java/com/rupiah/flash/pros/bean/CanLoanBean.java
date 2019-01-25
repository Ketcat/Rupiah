package com.rupiah.flash.pros.bean;

/**
 * Created by heise on 2018/8/7.
 */

public class CanLoanBean {

    /**
     * loan_qualification : false
     * successful_loan_times : 0
     * message : 还有未上传银行卡贷款
     * loan_repay : true
     * loan_app_id : 100060
     * loan_submitted : true
     * info : {loanAppId:'100060',interestAccr:'42000',serviceFeeAccr:'63000',
     * remainAmount:'600000.00000000'}
     * period : 7D
     * amount : 600000.0
     */
    private String error;
    private boolean loan_qualification;
    private int successful_loan_times;
    private String message;
    private boolean loan_repay;
    private int loan_app_id;
    private boolean loan_submitted;
    private String info;
    private String period;
    private double amount;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isLoan_qualification() {
        return loan_qualification;
    }

    public void setLoan_qualification(boolean loan_qualification) {
        this.loan_qualification = loan_qualification;
    }

    public int getSuccessful_loan_times() {
        return successful_loan_times;
    }

    public void setSuccessful_loan_times(int successful_loan_times) {
        this.successful_loan_times = successful_loan_times;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isLoan_repay() {
        return loan_repay;
    }

    public void setLoan_repay(boolean loan_repay) {
        this.loan_repay = loan_repay;
    }

    public int getLoan_app_id() {
        return loan_app_id;
    }

    public void setLoan_app_id(int loan_app_id) {
        this.loan_app_id = loan_app_id;
    }

    public boolean isLoan_submitted() {
        return loan_submitted;
    }

    public void setLoan_submitted(boolean loan_submitted) {
        this.loan_submitted = loan_submitted;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
