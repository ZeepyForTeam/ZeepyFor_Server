package com.zeepy.server.review.dto;

import java.util.List;

import com.zeepy.server.user.domain.Address;
import com.zeepy.server.user.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewUserResDto {
	private Long id;
	private String name;
	private List<Address> addresses;

	public ReviewUserResDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.addresses = user.getAddresses();
	}
}
