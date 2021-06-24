package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class RefreshTokenException extends CustomException {
	public RefreshTokenException() {
		super(ErrorCode.REFRESH_TOKEN_EXPIRED);
	}
}
