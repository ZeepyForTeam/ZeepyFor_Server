package com.zeepy.server.auth.dto;

import com.zeepy.server.user.domain.Role;
import com.zeepy.server.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
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
