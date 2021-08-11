package com.zeepy.server.user.dto;

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
	private Boolean isAddressCheck;

	@Builder
	public AddressDto(Address address) {
		this.cityDistinct = address.getCityDistinct();
		this.primaryAddress = address.getPrimaryAddress();
		this.isAddressCheck = address.getIsAddressCheck();
	}

	public void setIsAddressCheckToTrue() {
		this.isAddressCheck = true;
	}
}
