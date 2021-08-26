package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class AppleUnAuthrizationException extends CustomException {
	public AppleUnAuthrizationException() {
		super(ErrorCode.APPLE_UNAUTHORIZATION);
	}
}
