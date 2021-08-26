package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class SNSUnAuthorization extends CustomException {
	public SNSUnAuthorization() {
		super(ErrorCode.SNS_UNAUTHORIZATION);
	}
}
