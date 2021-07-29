package com.zeepy.server.user.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddAddressReqDto {
	private List<AddressDto> addresses;

	@Deprecated
	private Long userId;    //후에 토큰값으로 userEmail을 받을것임.

	@Builder
	public AddAddressReqDto(List<AddressDto> addresses, Long userId) {
		this.addresses = addresses;
		this.userId = userId;
	}
}
