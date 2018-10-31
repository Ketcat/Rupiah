package com.ptyl.uang.flash.bean;

/**
 * Created by heise on 2018/6/8.
 */

public class LoginBean {

    /**
     * token : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwODAwMDAwMDAwIiwiZXhwIjoxNTMwMjYyOTUxfQ
     * .JjI9UILUUBHfXu_RzGEypj2jyj0I2pk4u8oFnmYNt6DnelwlqS1Pek815KyXbXeiTk-ui7nfxecLRKl0JJE9mw
     */

    private String token;

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
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
