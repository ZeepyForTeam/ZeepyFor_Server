package com.zeepy.server.email.dto;

import javax.validation.constraints.Email;

import com.zeepy.server.email.domain.AdminEmail;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Minky on 2021-07-25
 */

@NoArgsConstructor
@Setter
@Getter
public class EmailRequestDto {
	@Email(message = "email cannot be Empty")
	String email;

	@Builder
	public EmailRequestDto(
		String email
	) {
		this.email = email;
	}

	public AdminEmail returnEmailEntity() {
		return new AdminEmail(null, this.email);
	}
}
