package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class BadRequestAddressException extends CustomException{
    public BadRequestAddressException() {
        super(ErrorCode.INVALID_ADDRESSES);
    }
}
