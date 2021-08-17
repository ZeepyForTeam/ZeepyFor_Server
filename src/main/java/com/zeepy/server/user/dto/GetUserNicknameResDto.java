package com.zeepy.server.user.dto;

import com.zeepy.server.user.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GetUserNicknameResDto {
	private String nickname;

	public GetUserNicknameResDto(User user) {
		this.nickname = user.getNickname();
	}
}
