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
	private String name;
	//	private String id;

	public User toEntity() {
		return User.builder()
			.email(email)
			.name(name)
			.profileImage("https://zeepy.s3.ap-northeast-2.amazonaws.com/zeepyImage/dummyprofile_28pt.png")
			.role(Role.ROLE_USER)
			.accessNotify(false)
			.sendMailCheck(false)
			.build();
	}
}
