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
public class BuildingAddressResponseDto {
    private Long id;
    private String apartmentName;
    private String shortAddress;
    private String address;

    public BuildingAddressResponseDto(
        Long id,
        String apartmentName,
        String shortAddress,
        String address
    ) {
        this.id = id;
        this.apartmentName = apartmentName;
        this.shortAddress = shortAddress;
        this.address = address;
    }

    public static BuildingAddressResponseDto of(Building building) {
        return new BuildingAddressResponseDto(
            building.getId(),
            building.getApartmentName(),
            building.getShortAddress(),
            building.getAddress()
        );
    }

    public static List<BuildingAddressResponseDto> listOf(List<Building> buildingList) {
        return buildingList
            .stream()
            .map(BuildingAddressResponseDto::of)
            .collect(Collectors.toList());
    }
}
