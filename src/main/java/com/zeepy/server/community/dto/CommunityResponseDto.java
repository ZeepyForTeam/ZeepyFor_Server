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
	private String productPrice;
	private String purchasePlace;
	private String sharingMethod;
	private Integer currentNumberOfPeople;
	private Integer targetNumberOfPeople;
	private String instructions;
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

	public CommunityResponseDto(Community community) {
		this.id = community.getId();
		this.user = new UserDto(
			community.getUser()
				.getId(),
			community.getUser()
				.getNickname(),
			community.getUser()
				.getProfileImage());
		this.communityCategory = community.getCommunityCategory();
		this.address = community.getAddress();
		this.productName = community.getProductName();
		this.productPrice = community.getProductPrice();
		this.purchasePlace = community.getPurchasePlace();
		this.sharingMethod = community.getSharingMethod();
		this.currentNumberOfPeople = community.getCurrentNumberOfPeople();
		this.targetNumberOfPeople = community.getTargetNumberOfPeople();
		this.instructions = community.getInstructions();
		this.title = community.getTitle();
		this.content = community.getContent();
	}

	@Builder
	public CommunityResponseDto(
		Long id,
		CommunityCategory communityCategory,
		String address,
		String productName,
		String productPrice,
		String purchasePlace,
		String sharingMethod,
		Integer currentNumberOfPeople,
		Integer targetNumberOfPeople,
		String instructions,
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
		this.productPrice = productPrice;
		this.purchasePlace = purchasePlace;
		this.sharingMethod = sharingMethod;
		this.currentNumberOfPeople = currentNumberOfPeople;
		this.targetNumberOfPeople = targetNumberOfPeople;
		this.instructions = instructions;
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
			.productPrice(community.getProductPrice())
			.purchasePlace(community.getPurchasePlace())
			.sharingMethod(community.getSharingMethod())
			.currentNumberOfPeople(community.getCurrentNumberOfPeople())
			.targetNumberOfPeople(community.getTargetNumberOfPeople())
			.instructions(community.getInstructions())
			.title(community.getTitle())
			.content(community.getContent())
			.comments(CommentResDto.listOf(community.getComments().stream()
				.filter(comment -> comment.getSuperComment() == null)
				.collect(Collectors.toList())))
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
