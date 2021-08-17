package com.zeepy.server.community.dto;

import java.time.LocalDateTime;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;
import com.zeepy.server.community.domain.CommunityLike;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityLikeResDto {
	private Long id;
	private CommunityCategory communityCategory;
	private String title;
	private String content;
	private LocalDateTime createdTime;
	private Boolean isCompleted;

	@Builder
	public CommunityLikeResDto(CommunityLike communityLike) {
		Community community = communityLike.getCommunity();
		this.id = communityLike.getId();
		this.communityCategory = community.getCommunityCategory();
		this.title = community.getTitle();
		this.content = community.getContent();
		this.createdTime = community.getCreatedDate();
		this.isCompleted = community.getCurrentNumberOfPeople().equals(community.getTargetNumberOfPeople());
	}
}
