package com.zeepy.server.building.dto;

import com.zeepy.server.building.domain.BuildingDeal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Minky on 2021-05-15
 */

@NoArgsConstructor
@Setter
@Getter
public class BuildingDealResponseDto {
    long id;
    LocalDateTime dealDate;
    int deposit;
    int monthlyRent;
    int floor;

    public BuildingDealResponseDto(
            long id,
            LocalDateTime dealDate,
            int deposit,
            int monthlyRent,
            int floor
    ) {
        this.id = id;
        this.dealDate = dealDate;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.floor = floor;
    }

    public static BuildingDealResponseDto of(BuildingDeal buildingDeal) {
        return new BuildingDealResponseDto(
                buildingDeal.getId(),
                buildingDeal.getDealDate(),
                buildingDeal.getDeposit(),
                buildingDeal.getMonthlyRent(),
                buildingDeal.getFloor()
        );
    }

    public static List<BuildingDealResponseDto> listOf(List<BuildingDeal> buildingDealList) {
        return buildingDealList
                .stream()
                .map(buildingDeal -> of(buildingDeal))
                .collect(Collectors.toList());
    }
}
