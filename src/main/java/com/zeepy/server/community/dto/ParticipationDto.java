package com.zeepy.server.community.dto;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.Participation;
import com.zeepy.server.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ParticipationDto {
	private Community community;
	private User user;

	public Participation toUpdateEntity() {
		Participation participation = Participation.builder()
			.build();
		community.addCurrentNumberOfPeople();
		participation.setCommunity(this.community);
		participation.setUser(this.user);
		this.community.getParticipationsList().add(participation);
		this.user.getParticipatingCommunities().add(participation);
		return participation;
	}

	public Participation toEntity() {
		Participation participation = Participation.builder()
			.build();
		participation.setCommunity(this.community);
		participation.setUser(this.user);
		this.community.getParticipationsList().add(participation);
		this.user.getParticipatingCommunities().add(participation);
		return participation;
	}
}
