package com.zeepy.server.building.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    private String shortAddress; // 동 번지 로 구성된 작은 주소

    @NotEmpty
    private String address; // 전체 주소

    @NotNull
    private float exclusivePrivateArea;

    @NotNull
    private int areaCode;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

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
        String address,
        float exclusivePrivateArea,
        int areaCode,
        double latitude,
        double longitude,
        List<Review> reviews
    ) {
        this.id = id;
        this.buildYear = buildYear;
        this.apartmentName = apartmentName;
        this.shortAddress = shortAddress;
        this.address = address;
        this.exclusivePrivateArea = exclusivePrivateArea;
        this.areaCode = areaCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reviews = reviews;
    }

    public void update(BuildingRequestDto buildingRequestDto) {
        this.buildYear = buildingRequestDto.getBuildYear();
        this.apartmentName = buildingRequestDto.getApartmentName();
        this.shortAddress = buildingRequestDto.getShortAddress();
        this.address = buildingRequestDto.getAddress();
        this.exclusivePrivateArea = buildingRequestDto.getExclusivePrivateArea();
        this.areaCode = buildingRequestDto.getAreaCode();
        this.latitude = buildingRequestDto.getLatitude();
        this.longitude = buildingRequestDto.getLongitude();
    }
}
