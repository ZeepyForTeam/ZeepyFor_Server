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

/**
 * DB 구조
 * 건설 날짜? -> 널러블
 * 주소
 * 전용 면적
 * 지역 코드
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
    private long id;

    private int buildYear; // Nullable

    @NotEmpty
    private String address;

    @NotNull
    private float exclusivePrivateArea;

    @NotNull
    private int areaCode;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    @OneToMany(mappedBy = "building", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BuildingDeal> beadingDeals;

    @Builder
    public Building(
            Long id,
            int buildYear,
            String address,
            float exclusivePrivateArea,
            int areaCode,
            double latitude,
            double longitude
    ) {
        this.id = id;
        this.buildYear = buildYear;
        this.address = address;
        this.exclusivePrivateArea = exclusivePrivateArea;
        this.areaCode = areaCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
