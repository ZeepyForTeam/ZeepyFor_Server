package com.zeepy.server.review.dto;

import com.zeepy.server.user.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewUserResDto {
	private Long id;
	private String name;
	private String address;

	public ReviewUserResDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.address = user.getAddress();
	}
}
