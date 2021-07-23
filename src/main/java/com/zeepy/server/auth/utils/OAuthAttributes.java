package com.zeepy.server.auth.utils;

import java.util.Map;

import com.zeepy.server.user.domain.Role;
import com.zeepy.server.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;

	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
	}

	public static OAuthAttributes of(String registrationId, String userNameAttributeName,
		Map<String, Object> attributes) {
		//SNS을 구분할 곳
		return ofKakao(userNameAttributeName, attributes);
	}

	public static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.attributes(attributes)
			.nameAttributeKey(userNameAttributeName)
			.name((String)attributes.get("name"))
			.email((String)attributes.get("email"))
			.build();
	}

	public User toEntity() {
		return User.builder()
			.name(name)
			.email(email)
			.role(Role.ROLE_USER)
			.build();
	}
}
