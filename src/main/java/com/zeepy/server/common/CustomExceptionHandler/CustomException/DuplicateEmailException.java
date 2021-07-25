package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class DuplicateEmailException extends CustomException {
	public DuplicateEmailException() {
		super(ErrorCode.Duplicate_Email);
	}
}
