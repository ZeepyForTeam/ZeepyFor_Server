package com.zeepy.server.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@Getter
public class LoginReqDto {
	@NotBlank(message = "이메일은 필수값 입니다.")
	private String email;
	@NotBlank(message = "비밀번호는 필수값 입니다.")
	private String password;

	public LoginReqDto(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
