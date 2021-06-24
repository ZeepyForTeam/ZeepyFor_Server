package com.zeepy.server.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReIssueReqDto {
	private String accessToken;
	private String refreshToken;
}
