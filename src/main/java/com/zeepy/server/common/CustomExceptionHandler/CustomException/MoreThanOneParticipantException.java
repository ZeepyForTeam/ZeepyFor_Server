package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class MoreThanOneParticipantException extends CustomException {
	public MoreThanOneParticipantException() {
		super(ErrorCode.MORE_THAN_ONE_PARTICIPANT);
	}
}
