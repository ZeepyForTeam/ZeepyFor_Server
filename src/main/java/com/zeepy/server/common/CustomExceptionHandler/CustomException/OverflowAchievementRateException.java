package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class OverflowAchievementRateException extends CustomException {
    public OverflowAchievementRateException() {
        super(ErrorCode.OVERFLOW_ACHIEVEMENT);
    }
}
