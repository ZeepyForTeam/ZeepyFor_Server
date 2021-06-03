package com.zeepy.server.building.domain;

import com.zeepy.server.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

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
            double longitude
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
    }
}
