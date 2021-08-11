package com.zeepy.server.community.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.zeepy.server.community.domain.Comment;
import com.zeepy.server.user.dto.UserDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentResDto {
	private Long id;
	private String comment;
	private Boolean isSecret;
	private Boolean isParticipation;
	private Long communityId;
	private Long superCommentId;
	private List<CommentResDto> subComments = new ArrayList<>();
	private UserDto writer;
	private LocalDateTime createdTime;

	@Builder
	public CommentResDto(
		Long id,
		String comment,
		Boolean isSecret,
		Boolean isParticipation,
		Long communityId,
		Long superCommentId,
		List<CommentResDto> subComments,
		UserDto writer,
		LocalDateTime createdTime) {
		this.id = id;
		this.comment = comment;
		this.isSecret = isSecret;
		this.isParticipation = isParticipation;
		this.communityId = communityId;
		this.superCommentId = superCommentId;
		this.subComments = subComments;
		this.writer = writer;
		this.createdTime = createdTime;
	}

	public static CommentResDto of(Comment comment) {
		return CommentResDto.builder()
			.id(comment.getId())
			.comment(comment.getComment())
			.isSecret(comment.getIsSecret())
			.isParticipation(comment.getIsParticipation())
			.communityId(comment.getCommunity().getId())
			.superCommentId((comment.getSuperComment() == null) ? null : comment.getSuperComment().getId())
			.subComments(CommentResDto.listOf(comment.getSubComments()))
			.writer(UserDto.of(comment.getUser()))
			.createdTime(comment.getCreatedDate())
			.build();
	}

	public static List<CommentResDto> listOf(List<Comment> comments) {
		return comments.stream()
			.map(CommentResDto::of)
			.collect(Collectors.toList());
	}
}
