package com.zeepy.server.community.dto;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityLike;
import com.zeepy.server.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommunityLikeDto {

	private User user;
	private Community community;

	public CommunityLike toEntity() {
		CommunityLike communityLike = CommunityLike.builder().build();
		communityLike.setCommunity(community);
		communityLike.setUser(user);
		return communityLike;
	}
}
