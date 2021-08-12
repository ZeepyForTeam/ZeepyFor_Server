package com.zeepy.server.user.dto;

import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zeepy.server.user.domain.Role;
import com.zeepy.server.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RegistrationReqDto {
	@NotNull(message = "이름은 필수값입니다.")
	private String name;
	@NotNull(message = "이메일은 필수값입니다.")
	private String email;
	@NotNull(message = "비밀번호는 필수값입니다.")
	private String password;

	@Builder
	public RegistrationReqDto(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public User toEntity() {
		return User.builder()
			.name(name)
			.email(email)
			.password(setBCryptEncoding())
			.role(Role.ROLE_USER)
			// 모든 사용자 기본 프로필 이미지로 설정 -> 추후 기능 업데이트 시 변경 예정
			.profileImage("https://zeepy.s3.ap-northeast-2.amazonaws.com/zeepyImage/dummyprofile_28pt.png")
			.build();
	}

	private String setBCryptEncoding() {
		return new BCryptPasswordEncoder().encode(password);
	}
}
