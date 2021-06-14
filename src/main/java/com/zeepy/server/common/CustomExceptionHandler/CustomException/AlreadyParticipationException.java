package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class AlreadyParticipationException extends CustomException {
	public AlreadyParticipationException() {
		super(ErrorCode.ALREADY_PARTICIPATION);
	}
}
