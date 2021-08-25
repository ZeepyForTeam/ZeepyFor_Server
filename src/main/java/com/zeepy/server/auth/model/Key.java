package com.zeepy.server.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Key {
	private String alg;
	private String e;
	private String kid;
	private String kty;
	private String n;
	private String use;
}
