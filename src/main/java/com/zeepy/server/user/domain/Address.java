package com.zeepy.server.user.domain;

import javax.persistence.Embeddable;

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
}
