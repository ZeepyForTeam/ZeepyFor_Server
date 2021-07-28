package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

/**
 * Created by Minky on 2021-07-16
 */
public class InvalidRequestParameterException extends CustomException {
    public InvalidRequestParameterException() {
        super(ErrorCode.INVALID_PARAMETER);
    }
}
