package com.zeepy.server.auth.utils;

import java.io.Serializable;

import com.zeepy.server.user.domain.User;

public class SessionUser implements Serializable {
	private String name;
	private String email;

	public SessionUser(User user) {
		this.name = user.getName();
		this.email = user.getEmail();
	}
}
