package com.zeepy.server.user.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddAddressReqDto {
	private List<AddressDto> addresses;

	@Builder
	public AddAddressReqDto(List<AddressDto> addresses) {
		this.addresses = addresses;
	}

	public void validateAddresses() {
		if (addresses.size() == 1) {
			addresses.get(0).setIsAddressCheckToTrue();
		}
	}
}
