package com.zeepy.server.community.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyZipJoinResDto {
	private List<CommunitySimpleResDto> participatedCommunities;
	private List<CommunitySimpleResDto> myCommunities;

	@Builder
	public MyZipJoinResDto(List<CommunitySimpleResDto> participatedCommunities, List<CommunitySimpleResDto> myCommunities) {
		this.participatedCommunities = participatedCommunities;
		this.myCommunities = myCommunities;
	}
}
