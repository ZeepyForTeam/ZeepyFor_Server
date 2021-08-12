package com.zeepy.server.user.dto;

import com.zeepy.server.user.domain.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserDto {
	private Long id;
	private String name;
	private String profileImage;

	@Builder
	public UserDto(Long id, String name, String profileImage) {
		this.id = id;
		this.name = name;
		this.profileImage = profileImage;
	}

	public static UserDto of(User user) {
		return UserDto.builder()
			.id(user.getId())
			.name(user.getName())
			.profileImage(user.getProfileImage())
			.build();
	}
}
