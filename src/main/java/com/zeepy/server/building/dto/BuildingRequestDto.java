package com.zeepy.server.building.dto;

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
    private String apartmentName;
    private String address;
    private float exclusivePrivateArea;
    private int areaCode;
    private double latitude;
    private double longitude;

    public BuildingRequestDto(
            int buildYear,
            String apartmentName,
            String address,
            float exclusivePrivateArea,
            int areaCode,
            double latitude,
            double longitude
    ) {
        this.buildYear = buildYear;
        this.apartmentName = apartmentName;
        this.address = address;
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
                this.address,
                this.exclusivePrivateArea,
                this.areaCode,
                this.latitude,
                this.longitude
        );
    }
}
