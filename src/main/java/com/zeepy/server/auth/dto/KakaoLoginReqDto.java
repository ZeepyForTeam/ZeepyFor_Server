package com.zeepy.server.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class KakaoLoginReqDto {
	private String accessToken;

	public KakaoLoginReqDto(String accessToken) {
		this.accessToken = accessToken;
	}
}
