package com.zeepy.server.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AppleTokenResDto {
	private String accessToken;
	private String refreshToken;
	private String appleRefreshToken;
	private Long userId;
}
