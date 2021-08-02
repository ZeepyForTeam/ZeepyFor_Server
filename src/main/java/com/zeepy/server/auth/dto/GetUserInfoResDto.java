package com.zeepy.server.auth.dto;

import com.zeepy.server.user.domain.Role;
import com.zeepy.server.user.domain.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class GetUserInfoResDto {
	private String email;

	public User toEntity() {
		return User.builder()
			.email(email)
			.role(Role.ROLE_USER)
			.build();
	}
}
