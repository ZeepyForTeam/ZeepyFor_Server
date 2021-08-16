package com.zeepy.server.building.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zeepy.server.building.domain.Building;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Minky on 2021-05-15
 */

@NoArgsConstructor
@Setter
@Getter
public class BuildingRequestDto {
	private int buildYear; // Null 이 될 가능성이 있음

	@NotBlank(message = "apartmentName cannot be Empty")
	private String apartmentName;

	@NotBlank(message = "shortAddress cannot be Empty")
	private String shortAddress; // 시 + 구 이름으로 구성된 주소

	@NotBlank(message = "fullRoadNameAddress cannot be Empty")
	private String fullRoadNameAddress; // 전체 도로명 주소

	@NotBlank(message = "shortRoadNameAddress cannot be Empty")
	private String shortRoadNameAddress; // 도로명 + 아파트 이름으로 구성된 주소

	@NotBlank(message = "fullNumberAddress cannot be Empty")
	private String fullNumberAddress; // 전체 지번 주소

	@NotBlank(message = "shortNumberAddress cannot be Empty")
	private String shortNumberAddress; // 지번 + 아파트 이름으로 구성된 주소

	@NotNull(message = "exclusivePrivateArea cannot be Null")
	private float exclusivePrivateArea;

	@NotNull(message = "areaCode cannot be Null")
	private int areaCode;

	@NotNull(message = "latitude cannot be Null")
	private double latitude;

	@NotNull(message = "longitude cannot be Null")
	private double longitude;

	public BuildingRequestDto(
		int buildYear,
		String apartmentName,
		String shortAddress,
		String fullRoadNameAddress,
		String shortRoadNameAddress,
		String fullNumberAddress,
		String shortNumberAddress,
		float exclusivePrivateArea,
		int areaCode,
		double latitude,
		double longitude
	) {
		this.buildYear = buildYear;
		this.apartmentName = apartmentName;
		this.shortAddress = shortAddress;
		this.fullRoadNameAddress = fullRoadNameAddress;
		this.shortRoadNameAddress = shortRoadNameAddress;
		this.fullNumberAddress = fullNumberAddress;
		this.shortNumberAddress = shortNumberAddress;
		this.exclusivePrivateArea = exclusivePrivateArea;
		this.areaCode = areaCode;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Building returnBuildingEntity() {
		return new Building(
			null,
			this.buildYear,
			this.apartmentName,
			this.shortAddress,
			this.fullRoadNameAddress,
			this.shortRoadNameAddress,
			this.fullNumberAddress,
			this.shortNumberAddress,
			this.exclusivePrivateArea,
			this.areaCode,
			this.latitude,
			this.longitude
		);
	}
}
