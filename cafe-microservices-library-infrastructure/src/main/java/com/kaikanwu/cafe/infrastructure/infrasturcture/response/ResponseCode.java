package com.kaikanwu.cafe.infrastructure.infrasturcture.response;

public enum ResponseCode{

    SUCCESS(0,"Success"),
    INTERNAL_SERVER_ERROR(-1, "Internal Server Error");

    private final Integer code;
    private final String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
