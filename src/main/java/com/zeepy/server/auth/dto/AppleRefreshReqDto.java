package com.zeepy.server.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AppleRefreshReqDto {
	private String appleRefreshToken;
}
