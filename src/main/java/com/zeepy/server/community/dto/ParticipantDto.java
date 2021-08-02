package com.zeepy.server.community.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.zeepy.server.community.domain.Participation;
import com.zeepy.server.user.dto.UserDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ParticipantDto {
	private Long id;
	private UserDto user;

	@Builder
	public ParticipantDto(Long id, UserDto user) {
		this.id = id;
		this.user = user;
	}

	public static ParticipantDto of(Participation participation) {
		return ParticipantDto.builder()
			.id(participation.getId())
			.user(new UserDto(participation.getUser().getId(), participation.getUser().getName()))
			.build();
	}

	public static List<ParticipantDto> listOf(List<Participation> participationList) {
		return participationList.stream()
			.map(ParticipantDto::of)
			.collect(Collectors.toList());
	}
}
