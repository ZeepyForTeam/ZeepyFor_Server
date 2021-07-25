package com.zeepy.server.user.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ModifyPasswordReqDto {
	private String password;

	private String setBCryptEncoding(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}

	public String getPassword() {
		return setBCryptEncoding(this.password);
	}
}
