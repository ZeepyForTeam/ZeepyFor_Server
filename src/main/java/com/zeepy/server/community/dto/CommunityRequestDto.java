package com.zeepy.server.community.dto;

import javax.validation.constraints.NotNull;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityRequestDto {

	@NotNull(message = "주소는 필수입니다.")
	String address;

	CommunityCategory communityCategory;

	public CommunityRequestDto(@NotNull(message = "주소는 필수입니다.") String address,
		CommunityCategory communityCategory) {
		this.address = address;
		this.communityCategory = communityCategory;
	}
}
