package com.zeepy.server.community.dto;

import java.util.List;

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
	private String productName;
	private Integer productPrice;
	private String sharingMethod;
	private Integer targetNumberOfPeople;
	private String title;
	private String content;
	private UserDto user;
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
		this.productName = community.getProductName();
		this.productPrice = community.getProductPrice();
		this.sharingMethod = community.getSharingMethod();
		this.targetNumberOfPeople = community.getTargetNumberOfPeople();
		this.title = community.getTitle();
		this.content = community.getContent();
		this.comments = community.getComments();
		this.participationList = community.getParticipationsList();
		this.imageUrls = community.getImageUrls();
	}
}