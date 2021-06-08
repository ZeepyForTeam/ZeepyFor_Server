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
	private Long userId;

	@Builder
	public CommunityLikeRequestDto(Long communityId, Long userId) {
		this.communityId = communityId;
		this.userId = userId;
	}

}