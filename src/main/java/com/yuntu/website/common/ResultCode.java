package com.yuntu.website.common;

public enum ResultCode {

    OK(10000, "OK"),
    FAIL(10001,"FAIL"),
    ;


    // 结果状态码
    private Integer code;

    // 结果消息
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
