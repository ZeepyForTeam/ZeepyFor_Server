package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class NotFoundParticipationException extends CustomException {
    public NotFoundParticipationException() {
        super(ErrorCode.NOT_FOUND_PARTICIPATION);
    }
}
