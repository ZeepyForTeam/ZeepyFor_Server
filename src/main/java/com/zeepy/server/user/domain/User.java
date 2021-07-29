package com.zeepy.server.user.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

	private String place;

	@ElementCollection
	@CollectionTable(name = "user_address", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "address")
	private List<Address> addresses = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<CommunityLike> likedCommunities = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Participation> participatingCommunities = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Community> communities = new ArrayList<>();

	@Builder
	public User(Long id, String name, String place) {
		this.id = id;
		this.name = name;
		this.place = place;
	}

	public void setAddress(List<Address> addresses) {
		this.addresses = addresses;
	}
}
