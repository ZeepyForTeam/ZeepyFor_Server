package com.zeepy.server.community.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommunityResponseDtos {
	private List<CommunityResponseDto> communityResponseDtoList;

	public CommunityResponseDtos(List<CommunityResponseDto> communityResponseDtoList) {
		this.communityResponseDtoList = communityResponseDtoList;
	}

}
