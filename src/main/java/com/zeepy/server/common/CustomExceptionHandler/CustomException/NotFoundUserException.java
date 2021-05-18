package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class NotFoundUserException extends CustomException{
    public NotFoundUserException(){
        super(ErrorCode.NOT_FOUND_USER);
    }
}
