package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class NotFoundReviewException extends CustomException{
	public NotFoundReviewException() {
		super(ErrorCode.NOT_FOUND_REVIEW);
	}
}
