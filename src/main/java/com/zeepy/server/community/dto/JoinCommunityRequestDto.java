package com.zeepy.server.community.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JoinCommunityRequestDto {
	@NotBlank(message = "빈 댓글은 사용할수 없습니다.")
	private String comment;

	private Boolean isSecret;
}
