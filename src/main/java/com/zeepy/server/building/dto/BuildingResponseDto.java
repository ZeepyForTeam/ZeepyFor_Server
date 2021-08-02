package com.zeepy.server.building.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.zeepy.server.building.domain.Building;
import com.zeepy.server.review.dto.ReviewResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Minky on 2021-05-15
 */

@NoArgsConstructor
@Setter
@Getter
public class BuildingResponseDto {
    private Long id;
    private int buildYear; // Null 이 될 가능성이 있음
    private String apartmentName;
    private String shortAddress;
    private String address;
    private float exclusivePrivateArea;
    private int areaCode;
    private double latitude;
    private double longitude;
    private List<BuildingDealResponseDto> buildingDeals;
    private List<BuildingLikeResponseDto> buildingLikes;
    private List<ReviewResponseDto> reviews;

    public BuildingResponseDto(
        Long id,
        int buildYear,
        String apartmentName,
        String shortAddress,
        String address,
        float exclusivePrivateArea,
        int areaCode,
        double latitude,
        double longitude,
        List<BuildingDealResponseDto> buildingDeals,
        List<BuildingLikeResponseDto> buildingLikes,
        List<ReviewResponseDto> reviews
    ) {
        this.id = id;
        this.buildYear = buildYear;
        this.apartmentName = apartmentName;
        this.shortAddress = shortAddress;
        this.address = address;
        this.exclusivePrivateArea = exclusivePrivateArea;
        this.areaCode = areaCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.buildingDeals = buildingDeals;
        this.buildingLikes = buildingLikes;
        this.reviews = reviews;
    }

    public static BuildingResponseDto of(Building building) {
        return new BuildingResponseDto(
            building.getId(),
            building.getBuildYear(),
            building.getApartmentName(),
            building.getShortAddress(),
            building.getAddress(),
            building.getExclusivePrivateArea(),
            building.getAreaCode(),
            building.getLatitude(),
            building.getLongitude(),
            BuildingDealResponseDto.listOf(building.getBuildingDeals()),
            BuildingLikeResponseDto.listOf(building.getBuildingLikes()),
            ReviewResponseDto.listOf(building.getReviews())
        );
    }

    public static List<BuildingResponseDto> listOf(List<Building> buildingList) {
        return buildingList
            .stream()
            .map(BuildingResponseDto::of)
            .collect(Collectors.toList());
    }
}
