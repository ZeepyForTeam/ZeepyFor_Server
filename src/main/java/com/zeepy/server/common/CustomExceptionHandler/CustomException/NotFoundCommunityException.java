package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class NotFoundCommunityException extends CustomException {
    public NotFoundCommunityException() {
        super(ErrorCode.NOT_FOUND_COMMUNITY);
    }
}
