package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class KakaoUnAuthorization extends CustomException {
	public KakaoUnAuthorization() {
		super(ErrorCode.KAKAO_UNAUTHORIZATION);
	}
}
