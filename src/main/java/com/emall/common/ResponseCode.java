package com.emall.common;

/**
 * Created by Administrator on 2017/11/28 0028.
 */
public enum ResponseCode {
    SUCCESS(0,"SUCCESS"),
    FAIL(1,"Fail"),
    Illegal(2,"Illegal parameter"),
    NOLOGIN(10,"NO-LOGIN");
    private final int code;
    private final String value;

    ResponseCode(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
