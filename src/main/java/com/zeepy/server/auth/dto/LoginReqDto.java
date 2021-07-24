package com.zeepy.server.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginReqDto {
	private String email;
	private String password;

	public LoginReqDto(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
