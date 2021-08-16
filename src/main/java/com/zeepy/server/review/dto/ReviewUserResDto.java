package com.zeepy.server.review.dto;

import java.util.List;

import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.dto.AddressDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewUserResDto {
	private Long id;
	private String name;
	private List<AddressDto> addresses;

	public ReviewUserResDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.addresses = AddressDto.listOf(user.getAddresses());
	}
}
