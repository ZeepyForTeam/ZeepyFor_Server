package com.zeepy.server.community.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyZipResponseDto {
	private List<CommunitySimpleResDto> myZip = new ArrayList<>();

	@Builder
	public MyZipResponseDto(List<CommunitySimpleResDto> myZip) {
		this.myZip = myZip;
	}

	public void addCommunities(List<Community> communityList) {
		myZip.addAll(CommunitySimpleResDto.listOf(communityList));
		myZip = myZip.stream()
			.distinct()
			.collect(Collectors.toList());
	}

	public void filtering(CommunityCategory communityCategory) {
		myZip = myZip.stream()
			.filter(c -> c.getCommunityCategory().equals(communityCategory))
			.collect(Collectors.toList());
	}
}
