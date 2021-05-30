package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class NoSuchUserException extends CustomException{
    public NoSuchUserException(){
        super(ErrorCode.NO_SUCH_USER);
    }
}
