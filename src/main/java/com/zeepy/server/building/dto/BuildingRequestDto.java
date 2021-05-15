package com.zeepy.server.building.dto;

import com.zeepy.server.building.domain.Building;
import com.zeepy.server.building.domain.BuildingDeal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Minky on 2021-05-15
 */

@NoArgsConstructor
@Setter
@Getter
public class BuildingRequestDto {
    private int buildYear; // Null 이 될 가능성이 있음
    private String address;
    private float exclusivePrivateArea;
    private int areaCode;

    public BuildingRequestDto(
            int buildYear,
            String address,
            float exclusivePrivateArea,
            int areaCode
    ) {
        this.buildYear = buildYear;
        this.address = address;
        this.exclusivePrivateArea = exclusivePrivateArea;
        this.areaCode = areaCode;
    }

    public Building returnBuildingEntity() {
        return new Building(
                null,
                this.buildYear,
                this.address,
                this.exclusivePrivateArea,
                this.areaCode
        );
    }
}
