package com.kaikanwu.cafe.infrastructure.infrasturcture.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response{

    public static Integer SUCCESS_CODE = 0;
    public static Integer FAILURE_CODE = -1;

    private int code;
    private String message;
    private Object data;

    public static Response success() {

        return Response.builder()
                .code(ResponseCode.SUCCESS.code())
                .message(ResponseCode.SUCCESS.message())
                .build();
    }

    public static Response success(Object data) {
        return Response.builder()
                .code(ResponseCode.SUCCESS.code())
                .message(ResponseCode.SUCCESS.message())
                .data(data)
                .build();
    }

    public static Response error(int code, String message) {
        return Response.builder()
                .code(code)
                .message(message)
                .build();
    }


}
