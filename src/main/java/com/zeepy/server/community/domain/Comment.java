package com.zeepy.server.community.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.zeepy.server.common.domain.BaseTimeEntity;
import com.zeepy.server.user.domain.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String comment;

	private Boolean isSecret;

	@Column(columnDefinition = "boolean default false")
	private Boolean isParticipation;

	@ManyToOne
	@JoinColumn(name = "community_id")
	private Community community;

	@ManyToOne
	@JoinColumn(name = "super_comment_id")
	private Comment superComment;

	@OneToMany(mappedBy = "superComment")
	private List<Comment> subComments = new ArrayList<>();

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Builder
	public Comment(Long id,
		String comment,
		Boolean isSecret,
		Boolean isParticipation,
		Community community,
		User user,
		Comment superComment) {
		this.id = id;
		this.comment = comment;
		this.isSecret = isSecret;
		this.isParticipation = isParticipation;
		this.community = community;
		this.user = user;
		this.superComment = superComment;
	}

	public void setSuperComment(Comment superComment) {
		this.superComment = superComment;
		if (superComment != null) {
			superComment.getSubComments().add(this);
		}
	}

	public void setCommunity(Community community) {
		this.community = community;
		if (this.superComment == null) {
			community.getComments().add(this);
		}
	}

	public void cancelParticipation() {
		this.isParticipation = false;
	}
}
