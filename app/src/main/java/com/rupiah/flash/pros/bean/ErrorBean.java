package com.rupiah.flash.pros.bean;

/**
 * Created by heise on 2018/6/13.
 */

public class ErrorBean {

    /**
     * error : err.rec
     * message : Aplikasi Anda adalah dalam proses, selama waktu ini Anda tidak dapat mengubah
     * informasi pribadi Anda.
     */

    private String error;
    private String message;

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
}
