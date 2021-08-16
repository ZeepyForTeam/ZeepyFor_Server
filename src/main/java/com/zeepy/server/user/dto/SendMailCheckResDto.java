package com.zeepy.server.user.dto;

import com.zeepy.server.user.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SendMailCheckResDto {
	private Boolean sendMailCheck;

	public SendMailCheckResDto(User user) {
		this.sendMailCheck = user.getSendMailCheck();
	}
}
