package com.ptyl.uang.flash.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by heise on 2018/6/11.
 */

public class LoanListBean implements Serializable{
    private static final long serialVersionUID = -7060210544600464481L;

    /**
     * loanAppId : 100003
     * createTime : 2018-06-01 07:11:01
     * credentialNo : 1402241993333333
     * amount : 80000.0
     * period : 7
     * periodUnit : D
     * cost : 5600.015
     * totalAmount : 85600.0
     * paidAmount : 30000.0
     * remainAmount : 55600.0
     * dueAmount : 85600.0
     * minRepaymentAmount : 1324
     * dueDate : 2018-06-14T05:38:00Z
     * remainingDays : -25
     * bankCode : BCA
     * cardNo : 805073259
     * status : CURRENT
     * statusLogs : [{"status":"Periode Pengembalian","createTime":"2018-06-07T05:37:03Z"},
     * {"status":"Sedang Di-review","createTime":"2018-06-01T07:11:13Z"},{"status":"Telah
     * Disampaikan","createTime":"2018-06-01T07:11:01Z"}]
     * comments : null
     * punishmentAmount : 0.0
     * repaymentType : BIAYA_ADMINISTRASI_AND_BUNGA_DIDEPAN_PEMBAYARAN_POKOK_DURASI_TETAP
     */

    private int loanAppId;
    private String createTime;
    private String credentialNo;
    private double amount;
    private int period;
    private String periodUnit;
    private double cost;
    private double totalAmount;
    private double paidAmount;
    private double remainAmount;
    private double dueAmount;
    private int minRepaymentAmount;
    private String dueDate;
    private int remainingDays;
    private String bankCode;
    private String cardNo;
    private String status;
    private Object comments;
    private double punishmentAmount;
    private String repaymentType;
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

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(double remainAmount) {
        this.remainAmount = remainAmount;
    }

    public double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public int getMinRepaymentAmount() {
        return minRepaymentAmount;
    }

    public void setMinRepaymentAmount(int minRepaymentAmount) {
        this.minRepaymentAmount = minRepaymentAmount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
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

    public double getPunishmentAmount() {
        return punishmentAmount;
    }

    public void setPunishmentAmount(double punishmentAmount) {
        this.punishmentAmount = punishmentAmount;
    }

    public String getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }

    public List<StatusLogsBean> getStatusLogs() {
        return statusLogs;
    }

    public void setStatusLogs(List<StatusLogsBean> statusLogs) {
        this.statusLogs = statusLogs;
    }

    public static class StatusLogsBean implements Serializable{
        /**
         * status : Periode Pengembalian
         * createTime : 2018-06-07T05:37:03Z
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
