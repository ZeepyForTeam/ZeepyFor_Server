package com.zeepy.server.community.dto;

import java.time.LocalDateTime;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WriteOutResDto {
	private Long id;
	private CommunityCategory communityCategory;
	private String title;
	private String content;
	private LocalDateTime createdTime;
	private Boolean isCompleted;

	@Builder
	public WriteOutResDto(Community community) {
		id = community.getId();
		communityCategory = community.getCommunityCategory();
		title = community.getTitle();
		content = community.getContent();
		createdTime = community.getCreatedDate();
		isCompleted = setIsCompleted(
			community.getCurrentNumberOfPeople(),
			community.getTargetNumberOfPeople());
	}

	public Boolean setIsCompleted(Integer currentNumberOfPeople, Integer targetNumberOfPeople) {
		if (targetNumberOfPeople == null) {
			return false;
		}
		return currentNumberOfPeople.equals(targetNumberOfPeople);
	}
}
