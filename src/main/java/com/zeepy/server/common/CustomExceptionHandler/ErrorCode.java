package com.zeepy.server.common.CustomExceptionHandler;

import lombok.Getter;

@Getter
public enum ErrorCode {
	INVALID_BODY(400, "Invalid Request Body 에러 발생!!"),
	INVALID_PARAMETER(400, "정상적인 Parameter가 아닙니다."),
	NO_CONTENT(400, "정상적인 Content가 아닙니다."),
	NOT_FOUND_ELEMENT(404, "찾고자하는 값이 업습니다."),
	NOT_FOUND_COMMUNITY(404, "찾고자하는 커뮤니티를 찾을수 없습니다."),
	NOT_FOUND_PARTICIPATION(404, "찾고자하는 참여자 목록을 찾을수 없습니다."),
	NOT_FOUND_USER(404, "찾고자하는 사용자를 찾을수 없습니다"),
	NOT_FOUND_PASSWORD(404, "비밀번호가 잘못되었습니다."),
	NOT_FOUND_REVIEW(404, "찾고자하는 리뷰를 찾을 수 없습니다"),
	BAD_REQUEST_COMMENT(400, "Comment에 대해 잘못된 요청입니다."),
	OVERFLOW_ACHIEVEMENT(400, "더이상 참여할수 없습니다."),
	ALREADY_PARTICIPATION(400, "이미 참여한 사용자입니다."),
	MORE_THAN_ONE_PARTICIPANT(400, "본인 제외 참여한 사람이 있어 글을 수정 또는 삭제할 수 없습니다."),
	REFRESH_TOKEN_EXPIRED(401, "RefreshToken의 유효시간이 만료되었습니다"),
	REFRESH_TOKEN_NOT_EXIST(400, "RefreshToken이 존재하지 않습니다. 다시 로그인 해주세요."),
	NOT_FOUND_TOKEN(401, "등록된 토큰이 존재하지 않습니다, 로그인을 다시해야합니다."),
	SNS_UNAUTHORIZATION(401, "잘못된 SNS 토큰입니다."),
	Duplicate_Email(400, "중복된 이메일입니다."),
	Duplicate_Nickname(400, "중복된 닉네임입니다."),
	APPLE_UNAUTHORIZATION(401, "애플 유효성 검사에 실패했습니다."),
	FCM_NOT_ALLOWED(500, "Firebase 오류입니다. 서버팀에 문의하여 주세요");

	private final int status;
	private final String message;

	ErrorCode(final int status, final String message) {
		this.status = status;
		this.message = message;
	}
}
