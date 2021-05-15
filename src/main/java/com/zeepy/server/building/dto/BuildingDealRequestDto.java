package com.zeepy.server.building.dto;

import com.zeepy.server.building.domain.BuildingDeal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Created by Minky on 2021-05-15
 */

@NoArgsConstructor
@Setter
@Getter
public class BuildingDealRequestDto {
    private LocalDateTime dealDate;
    private int deposit;
    private int monthlyRent;
    private int floor;

    public BuildingDealRequestDto(
            LocalDateTime dealDate,
            int deposit,
            int monthlyRent,
            int floor
    ) {
        this.dealDate = dealDate;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.floor = floor;
    }

    public BuildingDeal returnBuildingDealEntity() {
        return new BuildingDeal(
                null,
                this.dealDate,
                this.deposit,
                this.monthlyRent,
                this.floor
        );
    }
}
