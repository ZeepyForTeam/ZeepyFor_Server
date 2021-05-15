package com.zeepy.server.building.dto;

import com.zeepy.server.building.domain.Building;
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
    LocalDateTime dealDate;
    int deposit;
    int monthlyRent;
    Building building;

    public BuildingDealRequestDto(
            LocalDateTime dealDate,
            int deposit,
            int monthlyRent) {
        this.dealDate = dealDate;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
    }

    public BuildingDeal returnBuildingDealEntity() {
        return new BuildingDeal(
                null,
                this.dealDate,
                this.deposit,
                this.monthlyRent
        );
    }
}
