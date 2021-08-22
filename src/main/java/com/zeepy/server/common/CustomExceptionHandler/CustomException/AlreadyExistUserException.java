package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class AlreadyExistUserException extends CustomException {
	public AlreadyExistUserException() {
		super(ErrorCode.ALREADY_EXIST_USER);
	}
}
