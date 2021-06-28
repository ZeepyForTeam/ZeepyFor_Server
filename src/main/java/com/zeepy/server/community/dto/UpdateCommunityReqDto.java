package com.zeepy.server.community.dto;

import javax.validation.constraints.NotBlank;

import com.zeepy.server.community.domain.Community;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommunityReqDto {
	@NotBlank(message = "제목은 필수 사항입니다.")
	private String title;

	private String productName;

	private Integer productPrice;

	private String purchasePlace;

	private String sharingMethod;

	private Integer targetNumberOfPeople;

	private String instructions;

	public void updateCommunity(Community community) {
		community.update(title,
			productName,
			productPrice,
			purchasePlace,
			sharingMethod,
			targetNumberOfPeople,
			instructions);
	}
}
