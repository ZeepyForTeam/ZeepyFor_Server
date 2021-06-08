package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class NoSuchCommunityException extends CustomException {
	public NoSuchCommunityException() {
		super(ErrorCode.NO_SUCH_COMMUNITY);
	}
}
