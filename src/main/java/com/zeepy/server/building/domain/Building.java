package com.zeepy.server.building.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.SQLInsert;

import com.zeepy.server.building.dto.BuildingRequestDto;
import com.zeepy.server.common.domain.BaseTimeEntity;
import com.zeepy.server.review.domain.Review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Minky on 2021-05-15
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"building_id", "fullNumberAddress"})})
@SQLInsert(sql = "INSERT IGNORE INTO building(" +
    "CREATED_DATE  " +
    ",APARTMENT_NAME  " +
    ",AREA_CODE  " +
    ",BUILD_YEAR  " +
    ",BUILDING_TYPE  " +
    ",EXCLUSIVE_PRIVATE_AREA  " +
    ",FULL_NUMBER_ADDRESS  " +
    ",FULL_ROAD_NAME_ADDRESS  " +
    ",LATITUDE  " +
    ",LONGITUDE  " +
    ",SHORT_ADDRESS  " +
    ",SHORT_NUMBER_ADDRESS  " +
    ",SHORT_ROAD_NAME_ADDRESS " +
    ",BUILDING_ID   )" +
    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
public class Building extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "building_sequence_gen")
    @SequenceGenerator(name = "building_sequence_gen", sequenceName = "building_sequence")
    @Column(name = "building_id")
    private Long id;

    private int buildYear; // Nullable

    @NotEmpty
    private String apartmentName; // 아파트 이름

    @NotEmpty
    private String shortAddress; // 시 + 구 이름으로 구성된 주소

    @NotEmpty
    private String fullRoadNameAddress; // 전체 도로명 주소

    @NotEmpty
    private String shortRoadNameAddress; // 도로명 + 아파트 이름으로 구성된 주소

    @NotEmpty
    @Column(unique = true)
    private String fullNumberAddress; // 전체 지번 주소

    @NotEmpty
    private String shortNumberAddress; // 지번 + 아파트 이름으로 구성된 주소

    @NotNull
    private float exclusivePrivateArea;

    @NotNull
    private int areaCode;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BuildingType buildingType;

    @OneToMany(mappedBy = "building", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BuildingDeal> buildingDeals;

    @OneToMany(mappedBy = "building", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BuildingLike> buildingLikes;

    @OneToMany(mappedBy = "building", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Review> reviews;

    @Builder
    public Building(
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
        BuildingType buildingType
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
    }

    public void update(BuildingRequestDto buildingRequestDto) {
        this.buildYear = buildingRequestDto.getBuildYear();
        this.apartmentName = buildingRequestDto.getApartmentName();
        this.shortAddress = buildingRequestDto.getShortAddress();
        this.fullRoadNameAddress = buildingRequestDto.getFullRoadNameAddress();
        this.shortRoadNameAddress = buildingRequestDto.getShortRoadNameAddress();
        this.fullNumberAddress = buildingRequestDto.getFullNumberAddress();
        this.shortNumberAddress = buildingRequestDto.getShortNumberAddress();
        this.exclusivePrivateArea = buildingRequestDto.getExclusivePrivateArea();
        this.areaCode = buildingRequestDto.getAreaCode();
        this.latitude = buildingRequestDto.getLatitude();
        this.longitude = buildingRequestDto.getLongitude();
        this.buildingType = BuildingType.valueOf(buildingRequestDto.getBuildingType());
    }
}
