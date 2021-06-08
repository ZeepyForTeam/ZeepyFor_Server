package com.zeepy.server.common.CustomExceptionHandler;

import lombok.Getter;

@Getter
public enum ErrorCode {
	INVALID_BODY(400, "Invalid Request Body 에러 발생!!"),
	NO_CONTENT(404, "정상적인 Content가 아닙니다."),
	NO_SUCH_USER(404, "id에 해당하는 유저가 존재하지 않습니다."),
	NO_SUCH_COMMUNITY(404, "id에 해당하는 커뮤니티가 존재하지 않습니다.");

	private final int status;
	private final String message;

	ErrorCode(final int status, final String message) {
		this.status = status;
		this.message = message;
	}
}
