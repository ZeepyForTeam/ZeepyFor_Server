package com.zeepy.server.user.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zeepy.server.user.domain.Role;
import com.zeepy.server.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RegistrationReqDto {
	private String name;
	private String email;
	private String password;
	private String address;
	private String building;

	@Builder
	public RegistrationReqDto(String name, String email, String password, String address, String building) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.address = address;
		this.building = building;
	}

	public User toEntity() {
		return User.builder()
			.name(name)
			.email(email)
			.password(setBCryptEncoding())
			.address(address)
			.building(building)
			.role(Role.ROLE_USER)
			.build();
	}

	private String setBCryptEncoding() {
		return new BCryptPasswordEncoder().encode(password);
	}
}
