package com.zeepy.server.community.dto;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.stream.Collectors;

import com.zeepy.server.community.domain.Comment;
import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;
import com.zeepy.server.community.domain.Participation;
import com.zeepy.server.user.dto.UserDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommunityResponseDto {
	private Long id;
	private CommunityCategory communityCategory;
	private String address;
	private String productName;
	private Integer productPrice;
	private String sharingMethod;
	private Integer targetNumberOfPeople;
	private String title;
	private String content;
	private UserDto user;
	private Boolean isLiked;
	private Boolean isParticipant;
	private List<Comment> comments;
	private List<Participation> participationList;
	private List<String> imageUrls;

	public CommunityResponseDto(Community community) {
		this.id = community.getId();
		this.user = new UserDto(
			community.getUser()
				.getId(),
			community.getUser()
				.getName());
		this.communityCategory = community.getCommunityCategory();
		this.address = community.getAddress();
		this.productName = community.getProductName();
		this.productPrice = community.getProductPrice();
		this.sharingMethod = community.getSharingMethod();
		this.targetNumberOfPeople = community.getTargetNumberOfPeople();
		this.title = community.getTitle();
		this.content = community.getContent();
		// this.isLiked = isLiked(community);

		this.comments = community.getComments();
		this.participationList = community.getParticipationsList();
		this.imageUrls = community.getImageUrls();
	}

	// private Boolean isLiked(Community community) {
	// 	return community.getLikes().stream()
	// 		.filter();
	// }

	public static List<CommunityResponseDto> listOf(List<Community> communityList) {
		return communityList.stream()
			.map(CommunityResponseDto::new)
			.collect(Collectors.toList());
	}

}
