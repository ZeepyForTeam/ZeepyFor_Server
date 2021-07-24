package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class RefreshTokenNotExistException extends CustomException {
	public RefreshTokenNotExistException() {
		super(ErrorCode.REFRESH_TOKEN_NOT_EXIST);
	}
}
