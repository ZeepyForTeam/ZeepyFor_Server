package com.zeepy.server.common.CustomExceptionHandler;

import lombok.Getter;

@Getter
public enum ErrorCode {
	INVALID_BODY(400, "Invalid Request Body 에러 발생!!"),
	NO_CONTENT(400, "정상적인 Content가 아닙니다."),
	NOT_FOUND_ELEMENT(404, "찾고자하는 값이 업습니다."),
	NOT_FOUND_COMMUNITY(404, "찾고자하는 커뮤니티를 찾을수 없습니다."),
	NOT_FOUND_PARTICIPATION(404, "찾고자하는 참여자 목록을 찾을수 없습니다."),
	NOT_FOUND_USER(404, "찾고자하나는 사용자를 찾을수 없습니다"),
	NOT_FOUND_PASSWORD(404, "비밀번호가 잘못되었습니다."),
	BAD_REQUEST_COMMENT(400, "Comment에 대해 잘못된 요청입니다."),
	OVERFLOW_ACHIEVEMENT(400, "더이상 참여할수 없습니다."),
	ALREADY_PARTICIPATION(400, "이미 참여한 사용자입니다.");

	private final int status;
	private final String message;

	ErrorCode(final int status, final String message) {
		this.status = status;
		this.message = message;
	}
}
