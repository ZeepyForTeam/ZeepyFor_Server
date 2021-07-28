package com.zeepy.server.building.dto;

import com.zeepy.server.building.domain.Building;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    private String shortAddress;

    @NotBlank(message = "address cannot be Empty")
    private String address;

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
            String address,
            float exclusivePrivateArea,
            int areaCode,
            double latitude,
            double longitude
    ) {
        this.buildYear = buildYear;
        this.apartmentName = apartmentName;
        this.shortAddress = shortAddress;
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
                this.shortAddress,
                this.address,
                this.exclusivePrivateArea,
                this.areaCode,
                this.latitude,
                this.longitude,
                null
        );
    }
}
