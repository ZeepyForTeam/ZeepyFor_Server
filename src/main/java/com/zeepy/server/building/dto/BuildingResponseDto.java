package com.zeepy.server.building.dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.zeepy.server.building.domain.Building;
import com.zeepy.server.review.domain.CommuncationTendency;
import com.zeepy.server.review.domain.MultiChoiceReview;
import com.zeepy.server.review.dto.ReviewResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by Minky on 2021-05-15
 */

@NoArgsConstructor
@Setter
@Getter
public class BuildingResponseDto {
    private Long id;
    private int buildYear; // Null 이 될 가능성이 있음
    private String apartmentName;
    private String shortAddress;
    private String fullRoadNameAddress;
    private String shortRoadNameAddress;
    private String fullNumberAddress;
    private String shortNumberAddress;
    private float exclusivePrivateArea;
    private int areaCode;
    private double latitude;
    private double longitude;
    private String buildingType;
    private String averageCommunicationTendency;
    private String averageSoundInsulation;
    private String averagePest;
    private String averageLightning;
    private String averageWaterPressure;
    private List<BuildingDealResponseDto> buildingDeals;
    private List<BuildingLikeResponseDto> buildingLikes;
    private List<ReviewResponseDto> reviews;

    public BuildingResponseDto(
            Long id,
            int buildYear,
            String apartmentName,
            String shortAddress,
            String fullRoadNameAddress,
            String shortRoadNameAddress,
            String fullNumberAddress,
            String shortNumberAddress,
            float exclusivePrivateArea,
            int areaCode,
            double latitude,
            double longitude,
            String buildingType,
            String averageCommunicationTendency,
            String averageSoundInsulation,
            String averagePest,
            String averageLightning,
            String averageWaterPressure,
            List<BuildingDealResponseDto> buildingDeals,
            List<BuildingLikeResponseDto> buildingLikes,
            List<ReviewResponseDto> reviews
    ) {
        this.id = id;
        this.buildYear = buildYear;
        this.apartmentName = apartmentName;
        this.shortAddress = shortAddress;
        this.fullRoadNameAddress = fullRoadNameAddress;
        this.shortRoadNameAddress = shortRoadNameAddress;
        this.fullNumberAddress = fullNumberAddress;
        this.shortNumberAddress = shortNumberAddress;
        this.exclusivePrivateArea = exclusivePrivateArea;
        this.areaCode = areaCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.buildingType = buildingType;
        this.averageCommunicationTendency = averageCommunicationTendency;
        this.averageSoundInsulation = averageSoundInsulation;
        this.averagePest = averagePest;
        this.averageLightning = averageLightning;
        this.averageWaterPressure = averageWaterPressure;
        this.buildingDeals = buildingDeals;
        this.buildingLikes = buildingLikes;
        this.reviews = reviews;
    }

    public static BuildingResponseDto of(Building building) {

        return new BuildingResponseDto(
                building.getId(),
                building.getBuildYear(),
                building.getApartmentName(),
                building.getShortAddress(),
                building.getFullRoadNameAddress(),
                building.getShortRoadNameAddress(),
                building.getFullNumberAddress(),
                building.getShortNumberAddress(),
                building.getExclusivePrivateArea(),
                building.getAreaCode(),
                building.getLatitude(),
                building.getLongitude(),
                building.getBuildingType().name(),
                Objects.isNull(building.getAverageCommunicationTendency())
                        ? "" : building.getAverageCommunicationTendency().name(),
                Objects.isNull(building.getAverageSoundInsulation())
                        ? "" : building.getAverageSoundInsulation().name(),
                Objects.isNull(building.getAveragePest())
                        ? "" : building.getAveragePest().name(),
                Objects.isNull(building.getAverageLightning())
                        ? "" : building.getAverageLightning().name(),
                Objects.isNull(building.getAverageWaterPressure())
                        ? "" : building.getAverageWaterPressure().name(),
                BuildingDealResponseDto.listOf(building.getBuildingDeals()),
                BuildingLikeResponseDto.listOf(building.getBuildingLikes()),
                ReviewResponseDto.listOf(building.getReviews())
        );
    }

    public static List<BuildingResponseDto> listOf(List<Building> buildingList) {
        return buildingList
                .stream()
                .map(BuildingResponseDto::of)
                .collect(Collectors.toList());
    }
}
