package com.zeepy.server.building.dto;

import com.zeepy.server.building.domain.BuildingDeal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Minky on 2021-05-15
 */

@NoArgsConstructor
@Setter
@Getter
public class BuildingDealRequestDto {
    private Long dealDate;
    private int deposit;
    private int monthlyRent;
    private int floor;
    private Long buildingId;

    public BuildingDealRequestDto(
            Long dealDate,
            int deposit,
            int monthlyRent,
            int floor,
            Long buildingId
    ) {
        this.dealDate = dealDate;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.floor = floor;
        this.buildingId = buildingId;
    }

    public BuildingDeal returnBuildingDealEntity() {
        return new BuildingDeal(
                null,
                new Timestamp(this.dealDate),
                this.deposit,
                this.monthlyRent,
                this.floor
        );
    }
}
