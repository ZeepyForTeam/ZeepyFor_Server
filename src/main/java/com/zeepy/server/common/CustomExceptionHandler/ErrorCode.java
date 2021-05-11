package com.zeepy.server.common.CustomExceptionHandler;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_BODY(400,"Invalid Request Body 에러 발생!!");

    private final int status;
    private final String message;

    ErrorCode(final int status, final String message){
        this.status = status;
        this.message = message;
    }
}
