package com.zeepy.server.user.domain;

import javax.persistence.Embeddable;

import com.zeepy.server.user.dto.AddressDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Getter
public class Address {
	private String cityDistinct;
	private String primaryAddress;
	private String detailAddress;

	public Address(AddressDto addressDto) {
		this.cityDistinct = addressDto.getCityDistinct();
		this.primaryAddress = addressDto.getPrimaryAddress();
		this.detailAddress = addressDto.getDetailAddress();
	}
}
