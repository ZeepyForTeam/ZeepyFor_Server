package com.zeepy.server.community.dto;

import com.zeepy.server.community.domain.Comment;
import com.zeepy.server.community.domain.Community;
import com.zeepy.server.user.domain.User;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CommentDto {
	private String comment;
	private Boolean isSecret;
	private Boolean isParticipation;
	private Comment superComment;
	private Community community;
	private User writer;

	@Builder
	public CommentDto(String comment,
		Boolean isSecret,
		Boolean isParticipation,
		Comment superComment,
		Community community,
		User writer) {
		this.comment = comment;
		this.isSecret = isSecret;
		this.isParticipation = isParticipation;
		this.superComment = superComment;
		this.community = community;
		this.writer = writer;
	}

	public Comment toEntity() {
		Comment comment = Comment.builder()
			.comment(this.comment)
			.isSecret(this.isSecret)
			.isParticipation(this.isParticipation)
			.user(this.writer)
			.build();
		comment.setSuperComment(superComment);
		comment.setCommunity(community);
		return comment;
	}
}
