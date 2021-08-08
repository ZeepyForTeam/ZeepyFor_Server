package com.zeepy.server.community.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;
import com.zeepy.server.user.dto.UserDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommunityResponseDto {
	private Long id;
	private CommunityCategory communityCategory;
	private String address;
	private String productName;
	private String sharingMethod;
	private Integer targetNumberOfPeople;
	private String title;
	private String content;
	private UserDto user;
	private Boolean isLiked;
	private Boolean isParticipant;
	private List<CommentResDto> comments;
	private List<ParticipantDto> participants;
	private List<String> imageUrls;
	private LocalDateTime createdTime;
	private Boolean isCompleted;

	@Builder
	public CommunityResponseDto(
		Long id,
		CommunityCategory communityCategory,
		String address,
		String productName,
		String sharingMethod,
		Integer targetNumberOfPeople,
		String title,
		String content,
		UserDto user,
		Boolean isLiked,
		Boolean isParticipant,
		List<CommentResDto> comments,
		List<ParticipantDto> participants,
		List<String> imageUrls,
		LocalDateTime createdTime,
		Boolean isCompleted) {
		this.id = id;
		this.communityCategory = communityCategory;
		this.address = address;
		this.productName = productName;
		this.sharingMethod = sharingMethod;
		this.targetNumberOfPeople = targetNumberOfPeople;
		this.title = title;
		this.content = content;
		this.user = user;
		this.isLiked = isLiked;
		this.isParticipant = isParticipant;
		this.comments = comments;
		this.participants = participants;
		this.imageUrls = imageUrls;
		this.createdTime = createdTime;
		this.isCompleted = isCompleted;
	}

	public static CommunityResponseDto of(Community community) {
		return CommunityResponseDto.builder()
			.id(community.getId())
			.user(UserDto.of(community.getUser()))
			.communityCategory(community.getCommunityCategory())
			.address(community.getAddress())
			.productName(community.getProductName())
			.sharingMethod(community.getSharingMethod())
			.targetNumberOfPeople(community.getTargetNumberOfPeople())
			.title(community.getTitle())
			.content(community.getContent())
			.comments(CommentResDto.listOf(community.getComments()))
			.participants(ParticipantDto.listOf(community.getParticipationsList()))
			.imageUrls(community.getImageUrls())
			.createdTime(community.getCreatedDate())
			.isCompleted(community.getCurrentNumberOfPeople().equals(community.getTargetNumberOfPeople()))
			.build();
	}

	public static List<CommunityResponseDto> listOf(List<Community> communityList) {
		return communityList.stream()
			.map(CommunityResponseDto::of)
			.collect(Collectors.toList());
	}

	public void setLiked(Boolean liked) {
		isLiked = liked;
	}

	public void setParticipant(Boolean participant) {
		isParticipant = participant;
	}
}
