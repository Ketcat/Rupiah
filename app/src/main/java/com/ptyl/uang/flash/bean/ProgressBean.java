package com.ptyl.uang.flash.bean;

/**
 * Created by heise on 2018/6/13.
 */

public class ProgressBean {

    /**
     * personalInfoPart : true
     * employmentPart : false
     * contactPart : false
     * filePart : true
     * completed : false
     */
    private String error;
    private String message;
    private boolean personalInfoPart;
    private boolean employmentPart;
    private boolean contactPart;
    private boolean filePart;
    private boolean completed;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isPersonalInfoPart() {
        return personalInfoPart;
    }

    public void setPersonalInfoPart(boolean personalInfoPart) {
        this.personalInfoPart = personalInfoPart;
    }

    public boolean isEmploymentPart() {
        return employmentPart;
    }

    public void setEmploymentPart(boolean employmentPart) {
        this.employmentPart = employmentPart;
    }

    public boolean isContactPart() {
        return contactPart;
    }

    public void setContactPart(boolean contactPart) {
        this.contactPart = contactPart;
    }

    public boolean isFilePart() {
        return filePart;
    }

    public void setFilePart(boolean filePart) {
        this.filePart = filePart;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
