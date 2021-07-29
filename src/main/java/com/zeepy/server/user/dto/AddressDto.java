package com.zeepy.server.user.dto;

import com.zeepy.server.user.domain.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddressDto {
	private String cityDistinct;
	private String primaryAddress;
	private String detailAddress;

	public AddressDto(AddressDto addressDto) {
		this.cityDistinct = addressDto.getCityDistinct();
		this.primaryAddress = addressDto.getPrimaryAddress();
		this.detailAddress = addressDto.getDetailAddress();
	}

	public AddressDto(Address address) {
		this.cityDistinct = address.getCityDistinct();
		this.primaryAddress = address.getPrimaryAddress();
		this.detailAddress = address.getDetailAddress();
	}
}
