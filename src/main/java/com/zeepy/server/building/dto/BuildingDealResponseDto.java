package com.zeepy.server.building.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import com.zeepy.server.building.domain.BuildingDeal;
import com.zeepy.server.building.domain.DealType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Minky on 2021-05-15
 */

@NoArgsConstructor
@Setter
@Getter
public class BuildingDealResponseDto {
    private Long id;
    private Timestamp dealDate;
    private int deposit;
    private int monthlyRent;
    private int dealCost;
    private int floor;
    private DealType dealType;

    public BuildingDealResponseDto(
        Long id,
        Timestamp dealDate,
        int deposit,
        int monthlyRent,
        int dealCost,
        int floor,
        DealType dealType
    ) {
        this.id = id;
        this.dealDate = dealDate;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.dealCost = dealCost;
        this.floor = floor;
        this.dealType = dealType;
    }

    public static BuildingDealResponseDto of(BuildingDeal buildingDeal) {
        return new BuildingDealResponseDto(
            buildingDeal.getId(),
            buildingDeal.getDealDate(),
            buildingDeal.getDeposit(),
            buildingDeal.getMonthlyRent(),
            buildingDeal.getDealCost(),
            buildingDeal.getFloor(),
            buildingDeal.getDealType()
        );
    }

    public static List<BuildingDealResponseDto> listOf(List<BuildingDeal> buildingDealList) {
        return buildingDealList
            .stream()
            .map(BuildingDealResponseDto::of)
            .collect(Collectors.toList());
    }
}
