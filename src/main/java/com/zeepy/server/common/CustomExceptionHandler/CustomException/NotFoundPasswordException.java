package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class NotFoundPasswordException extends CustomException {
	public NotFoundPasswordException() {
		super(ErrorCode.NOT_FOUND_PASSWORD);
	}
}
