package com.zeepy.server.building.dto;

import com.zeepy.server.building.domain.Building;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Minky on 2021-05-15
 */

@NoArgsConstructor
@Setter
@Getter
public class BuildingResponseDto {
    private Long id;
    private int buildYear; // Null 이 될 가능성이 있음
    private String address;
    private float exclusivePrivateArea;
    private int areaCode;
    private double latitude;
    private double longitude;
    private List<BuildingDealResponseDto> buildingDeals;

    public BuildingResponseDto(
            Long id,
            int buildYear,
            String address,
            float exclusivePrivateArea,
            int areaCode,
            double latitude,
            double longitude,
            List<BuildingDealResponseDto> buildingDeals
    ){
        this.id = id;
        this.buildYear = buildYear;
        this.address = address;
        this.exclusivePrivateArea = exclusivePrivateArea;
        this.areaCode = areaCode;
        this.buildingDeals = buildingDeals;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static BuildingResponseDto of(Building building){
        return new BuildingResponseDto(
                building.getId(),
                building.getBuildYear(),
                building.getAddress(),
                building.getExclusivePrivateArea(),
                building.getAreaCode(),
                building.getLatitude(),
                building.getLongitude(),
                BuildingDealResponseDto.listOf(building.getBeadingDeals())
        );
    }

    public static List<BuildingResponseDto> listOf(List<Building> buildingList){
        return buildingList
                .stream()
                .map(building -> of(building))
                .collect(Collectors.toList());
    }
}