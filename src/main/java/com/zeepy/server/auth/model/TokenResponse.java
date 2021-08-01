package com.zeepy.server.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TokenResponse {
	private String access_token;
	private Long expires_in;
	private String id_token;
	private String refresh_token;
	private String token_type;
}
