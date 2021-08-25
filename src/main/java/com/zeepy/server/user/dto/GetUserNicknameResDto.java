package com.zeepy.server.user.dto;

import com.zeepy.server.user.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GetUserNicknameResDto {
	private String nickname;
	private String email;

	public GetUserNicknameResDto(User user) {
		this.nickname = user.getNickname();
		this.email = user.getEmail();
	}
}
