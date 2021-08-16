package com.zeepy.server.building.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.zeepy.server.building.domain.Building;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Minky on 2021-06-03
 */

@NoArgsConstructor
@Setter
@Getter
public class BuildingAutoCompleteResponseDto {
	private Long id;
	private String apartmentName;
	private String shortAddress;
	private String fullRoadNameAddress;
	private String shortRoadNameAddress;
	private String fullNumberAddress;
	private String shortNumberAddress;

	public BuildingAutoCompleteResponseDto(
		Long id,
		String apartmentName,
		String shortAddress,
		String fullRoadNameAddress,
		String shortRoadNameAddress,
		String fullNumberAddress,
		String shortNumberAddress
	) {
		this.id = id;
		this.apartmentName = apartmentName;
		this.shortAddress = shortAddress;
		this.fullRoadNameAddress = fullRoadNameAddress;
		this.shortRoadNameAddress = shortRoadNameAddress;
		this.fullNumberAddress = fullNumberAddress;
		this.shortNumberAddress = shortNumberAddress;
	}

	public static BuildingAutoCompleteResponseDto of(Building building) {
		return new BuildingAutoCompleteResponseDto(
			building.getId(),
			building.getApartmentName(),
			building.getShortAddress(),
			building.getFullRoadNameAddress(),
			building.getShortRoadNameAddress(),
			building.getFullNumberAddress(),
			building.getShortNumberAddress()
		);
	}

	public static List<BuildingAutoCompleteResponseDto> listOf(List<Building> buildingList) {
		return buildingList
			.stream()
			.map(BuildingAutoCompleteResponseDto::of)
			.collect(Collectors.toList());
	}
}
