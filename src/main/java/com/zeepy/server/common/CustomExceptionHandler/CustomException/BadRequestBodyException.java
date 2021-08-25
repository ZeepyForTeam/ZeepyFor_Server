package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class BadRequestBodyException extends CustomException {
	public BadRequestBodyException() {
		super(ErrorCode.INVALID_BODY);
	}
}
