package com.zeepy.server.user.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.zeepy.server.user.domain.Address;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddressResDto {
	private List<AddressDto> addresses;

	public AddressResDto(List<Address> addresses) {
		this.addresses = addresses.stream().map(AddressDto::new).collect(Collectors.toList());
	}
}
