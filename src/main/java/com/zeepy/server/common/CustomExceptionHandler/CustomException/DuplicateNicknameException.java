package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class DuplicateNicknameException extends CustomException {
	public DuplicateNicknameException() {
		super(ErrorCode.Duplicate_Nickname);
	}
}
