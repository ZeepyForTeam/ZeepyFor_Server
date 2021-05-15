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
    long id;
    int buildYear; // Null 이 될 가능성이 있음
    String address;
    float exclusivePrivateArea;
    int areaCode;
    List<BuildingDealResponseDto> buildingDeals;

    public BuildingResponseDto(
            long id,
            int buildYear,
            String address,
            float exclusivePrivateArea,
            int areaCode,
            List<BuildingDealResponseDto> buildingDeals
    ){
        this.id = id;
        this.buildYear = buildYear;
        this.address = address;
        this.exclusivePrivateArea = exclusivePrivateArea;
        this.areaCode = areaCode;
        this.buildingDeals = buildingDeals;
    }

    public static BuildingResponseDto of(Building building){
        return new BuildingResponseDto(
                building.getId(),
                building.getBuildYear(),
                building.getAddress(),
                building.getExclusivePrivateArea(),
                building.getAreaCode(),
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
