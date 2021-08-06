package com.zeepy.server.community.dto;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityLikeRequestDto {

	@NotNull(message = "communityId값은 필수입니다.")
	private Long communityId;

	@NotNull(message = "userId값은 필수입니다.")
	private String userEmail;

	@Builder
	public CommunityLikeRequestDto(Long communityId, String userEmail) {
		this.communityId = communityId;
		this.userEmail = userEmail;
	}

}