package com.zeepy.server.user.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityLike;
import com.zeepy.server.community.domain.Participation;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence_gen")
	@SequenceGenerator(name = "user_sequence_gen", sequenceName = "user_sequence")
	private Long id;

	private String name;

	@OneToMany(mappedBy = "user")
	private List<CommunityLike> likedCommunities = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Participation> participatingCommunities = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Community> communities = new ArrayList<>();

	@Builder
	public User(String name) {
		this.name = name;
	}
}
