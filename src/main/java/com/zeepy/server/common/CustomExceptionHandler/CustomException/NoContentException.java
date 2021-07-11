package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

import lombok.Getter;

@Getter
public class NoContentException extends CustomException {

	public NoContentException() {
		super(ErrorCode.NO_CONTENT);
	}
}
