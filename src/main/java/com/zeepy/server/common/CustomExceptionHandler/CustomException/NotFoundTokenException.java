package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class NotFoundTokenException extends CustomException {
	public NotFoundTokenException() {
		super(ErrorCode.NOT_FOUND_TOKEN);
	}
}
