package com.zeepy.server.user.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.zeepy.server.user.domain.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddressDto {
	private String cityDistinct;
	private String primaryAddress;
	private String detailAddress;

	@Builder
	public AddressDto(Address address) {
		this.cityDistinct = address.getCityDistinct();
		this.primaryAddress = address.getPrimaryAddress();
		this.detailAddress = address.getDetailAddress();
	}

	public static AddressDto of(Address address) {
		return AddressDto.builder()
			.address(address)
			.build();
	}

	public static List<AddressDto> listOf(List<Address> addresses) {
		return addresses.stream()
			.map(AddressDto::of)
			.collect(Collectors.toList());
	}
}
