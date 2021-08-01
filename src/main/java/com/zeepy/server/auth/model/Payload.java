package com.zeepy.server.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Payload {
	private String iss;
	private String aud;
	private Long exp;
	private Long iat;
	private String sub;
	private String nonce;
	private String c_hash;
	private String at_hahs;
	private String email;
	private String email_verified;
	private String is_private_email;
	private Long auth_time;
	private boolean nonce_supported;
}
