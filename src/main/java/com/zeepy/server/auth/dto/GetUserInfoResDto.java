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
//	private String email;
	private String id;

	public User toEntity() {
		return User.builder()
			.email(id)
			.role(Role.ROLE_USER)
			.accessNotify(false)
			.sendMailCheck(false)
			.build();
	}
}
