package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class BadRequestCommentException extends CustomException {
    public BadRequestCommentException() {
        super(ErrorCode.BAD_REQUEST_COMMENT);
    }
}
