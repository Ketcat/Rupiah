package com.rupiah.flash.pros.bean;

import java.util.List;

/**
 * Created by heise on 2018/6/9.
 */

public class LatestBean {

    /**
     * loanAppId : 100010
     * createTime : 2018-06-09T09:52:50Z
     * credentialNo : 3205215405890005
     * amount : 600000.0
     * period : 7
     * periodUnit : D
     * cost : 42000.015
     * totalAmount : 642000.015
     * paidAmount : null
     * remainAmount : null
     * dueAmount : 642000.0
     * minRepaymentAmount : null
     * dueDate : null
     * remainingDays : 0
     * bankCode : MANDIRI
     * cardNo : 664664646
     * status : IN_REVIEW
     * statusLogs : [{"status":"审批中","createTime":"2018-06-09T09:53:06Z"},{"status":"已提交",
     * "createTime":"2018-06-09T09:52:50Z"}]
     * comments : null
     */

    private int loanAppId;
    private String createTime;
    private String credentialNo;
    private double amount;
    private int period;
    private String periodUnit;
    private double cost;
    private double totalAmount;
    private Object paidAmount;
    private Object remainAmount;
    private double dueAmount;
    private Object minRepaymentAmount;
    private Object dueDate;
    private int remainingDays;
    private String bankCode;
    private String cardNo;
    private String status;
    private Object comments;
    private List<StatusLogsBean> statusLogs;

    public int getLoanAppId() {
        return loanAppId;
    }

    public void setLoanAppId(int loanAppId) {
        this.loanAppId = loanAppId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCredentialNo() {
        return credentialNo;
    }

    public void setCredentialNo(String credentialNo) {
        this.credentialNo = credentialNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getPeriodUnit() {
        return periodUnit;
    }

    public void setPeriodUnit(String periodUnit) {
        this.periodUnit = periodUnit;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Object getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Object paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Object getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(Object remainAmount) {
        this.remainAmount = remainAmount;
    }

    public double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public Object getMinRepaymentAmount() {
        return minRepaymentAmount;
    }

    public void setMinRepaymentAmount(Object minRepaymentAmount) {
        this.minRepaymentAmount = minRepaymentAmount;
    }

    public Object getDueDate() {
        return dueDate;
    }

    public void setDueDate(Object dueDate) {
        this.dueDate = dueDate;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getComments() {
        return comments;
    }

    public void setComments(Object comments) {
        this.comments = comments;
    }

    public List<StatusLogsBean> getStatusLogs() {
        return statusLogs;
    }

    public void setStatusLogs(List<StatusLogsBean> statusLogs) {
        this.statusLogs = statusLogs;
    }

    public static class StatusLogsBean {
        /**
         * status : 审批中
         * createTime : 2018-06-09T09:53:06Z
         */

        private String status;
        private String createTime;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
