package com.zeepy.server.community.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.zeepy.server.common.domain.BaseTimeEntity;
import com.zeepy.server.user.domain.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class CommunityLike extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "like_sequence_gen")
	@SequenceGenerator(name = "like_sequence_gen", sequenceName = "like_sequence")
	@Column(name = "communitylike_id")
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "community_id")
	private Community community;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Builder
	public CommunityLike(Long id, Community community, User user) {
		this.id = id;
		this.community = community;
		this.user = user;
	}

	public void setCommunity(Community community) {
		if (this.community != null) {
			this.community.getLikes().remove(this);
		}
		this.community = community;
		community.getLikes().add(this);
	}

	public void setUser(User user) {
		if (this.user != null) {
			this.user.getLikedCommunities().remove(this);
		}
		this.user = user;
		user.getLikedCommunities().add(this);
	}
}
