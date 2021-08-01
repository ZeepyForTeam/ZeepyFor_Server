package com.zeepy.server.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AppleServiceResDto {
	private String state;
	private String code;
	private String id_token;
	private String user;
}
