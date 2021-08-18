package com.zeepy.server.community.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityLikeResDtos {
	private List<CommunitySimpleResDto> communityLikeResDtoList;

	@Builder
	public CommunityLikeResDtos(List<CommunitySimpleResDto> CommunitySimpleResDtos) {
		this.communityLikeResDtoList = CommunitySimpleResDtos;
	}
}
