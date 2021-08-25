package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class InValidEmailException extends CustomException{
	public InValidEmailException() {
		super(ErrorCode.INVALID_EMAIL);
	}
}
