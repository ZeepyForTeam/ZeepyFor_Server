package com.zeepy.server.community.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityLikeResDtos {
	private List<CommunityLikeResDto> communityLikeResDtoList;

	@Builder
	public CommunityLikeResDtos(List<CommunityLikeResDto> communityLikeResDtoList) {
		this.communityLikeResDtoList = communityLikeResDtoList;
	}
}
