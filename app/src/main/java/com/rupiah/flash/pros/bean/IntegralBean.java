package com.rupiah.flash.pros.bean;

/**
 * Created by heise on 2018/8/22.
 */

public class IntegralBean {

    /**
     * code : SUCCESS
     * message : null
     * data : {integralValue:0}
     */

    private String code;
    private Object message;
    private String data;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
