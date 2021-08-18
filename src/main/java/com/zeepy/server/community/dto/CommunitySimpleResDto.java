package com.zeepy.server.community.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class CommunitySimpleResDto {
	@EqualsAndHashCode.Include
	private Long id;
	private CommunityCategory communityCategory;
	private String title;
	private String content;
	private LocalDateTime createdTime;
	private Boolean isCompleted;

	@Builder
	public CommunitySimpleResDto(
		Long id,
		CommunityCategory communityCategory,
		String title,
		String content,
		LocalDateTime createdTime,
		Boolean isCompleted) {
		this.id = id;
		this.communityCategory = communityCategory;
		this.title = title;
		this.content = content;
		this.createdTime = createdTime;
		this.isCompleted = isCompleted;
	}

	public static CommunitySimpleResDto of(Community community) {
		return new CommunitySimpleResDto(
			community.getId(),
			community.getCommunityCategory(),
			community.getTitle(),
			community.getContent(),
			community.getCreatedDate(),
			checkIfCompleted(community.getCurrentNumberOfPeople(), community.getTargetNumberOfPeople())
		);
	}

	public static List<CommunitySimpleResDto> listOf(List<Community> communityList) {
		return communityList.stream()
			.map(CommunitySimpleResDto::of)
			.collect(Collectors.toList());
	}

	private static Boolean checkIfCompleted(Integer currentNumberOfPeople, Integer targetNumberOfPeople) {
		if (targetNumberOfPeople == null) {
			return false;
		}
		return currentNumberOfPeople.equals(targetNumberOfPeople);
	}
}
