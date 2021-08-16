package com.zeepy.server.auth.dto;

import com.zeepy.server.user.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TokenResDto {
	private String accessToken;
	private String refreshToken;
	private Long userId;

	public TokenResDto(String accessToken, String refreshToken, User user) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.userId = user.getId();
	}
}
