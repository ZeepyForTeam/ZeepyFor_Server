package com.zeepy.server.building.dto;

import com.zeepy.server.building.domain.BuildingDeal;
import com.zeepy.server.building.domain.DealType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
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
    @NotNull(message = "dealDate cannot be Null")
    private Long dealDate;

    @NotNull(message = "deposit cannot be Null")
    private int deposit;

    @NotNull(message = "monthlyRent cannot be Null")
    private int monthlyRent;

    @NotNull(message = "floor cannot be Null")
    private int floor;

    @NotNull(message = "dealCost cannot be Null")
    private int dealCost;

    @NotNull(message = "buildingId cannot be Null")
    private Long buildingId;

    public BuildingDealRequestDto(
            Long dealDate,
            int deposit,
            int monthlyRent,
            int dealCost,
            int floor,
            Long buildingId
    ) {
        this.dealDate = dealDate;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.dealCost = dealCost;
        this.floor = floor;
        this.buildingId = buildingId;
    }

    public BuildingDeal returnBuildingDealEntity() {
        DealType dealType = DealType.MONTHLY;
        if (this.dealCost != 0) {
            dealType = DealType.DEAL;
        } else if (this.monthlyRent == 0) {
            dealType = DealType.JEONSE;
        }

        return new BuildingDeal(
                null,
                new Timestamp(this.dealDate),
                this.deposit,
                this.monthlyRent,
                this.dealCost,
                this.floor,
                dealType
        );
    }
}
