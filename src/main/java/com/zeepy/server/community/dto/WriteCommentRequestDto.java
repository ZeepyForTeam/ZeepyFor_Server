package com.zeepy.server.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WriteCommentRequestDto {
	private String comment;
	private Boolean isSecret;
	private Long superCommentId;
}
