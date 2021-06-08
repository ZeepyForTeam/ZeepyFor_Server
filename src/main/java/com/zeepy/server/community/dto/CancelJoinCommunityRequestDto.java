package com.zeepy.server.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CancelJoinCommunityRequestDto {
	private Long cancelUserId;
}
