package com.zeepy.server.building.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zeepy.server.building.domain.Building;
import com.zeepy.server.building.domain.BuildingDeal;
import com.zeepy.server.building.domain.BuildingType;
import com.zeepy.server.building.domain.DealType;
import com.zeepy.server.common.annotation.Enum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Minky on 2021-08-07
 */

@NoArgsConstructor
@Setter
@Getter
public class BuildingBulkRequestDto {
	@NotNull(message = "buildingId cannot be Null")
	private Long buildingId;

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

	@Enum(enumClass = BuildingType.class, ignoreCase = true, message = "BuildingType is not valid")
	private String buildingType;

	@NotNull(message = "dealDate cannot be Null")
	private Long dealDate;

	@NotNull(message = "deposit cannot be Null")
	private int deposit;

	@NotNull(message = "monthlyRent cannot be Null")
	private int monthlyRent;

	@NotNull(message = "floor cannot be Null")
	private int floor;

	@NotNull(message = "dealCost cannot be Null")
	private int dealCost;

	public BuildingBulkRequestDto(
		Long buildingId,
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
		double longitude,
		String buildingType,
		Long dealDate,
		int deposit,
		int monthlyRent,
		int dealCost,
		int floor
	) {
		this.buildingId = buildingId;
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
		this.buildingType = buildingType;
		this.dealDate = dealDate;
		this.deposit = deposit;
		this.monthlyRent = monthlyRent;
		this.dealCost = dealCost;
		this.floor = floor;
	}

	public Building returnBuildingEntity() {
		return new Building(
			this.buildingId,
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
			this.longitude,
			BuildingType.valueOf(this.buildingType)
		);
	}

	public BuildingDeal returnBuildingDealEntity() {
		DealType dealType = DealType.getDealType(dealCost, monthlyRent);

		return new BuildingDeal(
			null,
			new Timestamp(this.dealDate),
			this.deposit,
			this.monthlyRent,
			this.dealCost,
			this.floor,
			dealType
		);
	}
}
