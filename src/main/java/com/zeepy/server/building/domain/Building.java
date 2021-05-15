package com.zeepy.server.building.domain;

import com.zeepy.server.common.domain.BaseTimeEntity;
import com.zeepy.server.review.domain.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Minky on 2021-05-15
 */

/**
 * DB 구조
 * 건설 날짜
 * 주소
 * 전용 면적
 * 지역 코드
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Building extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "building_sequence_gen")
    @SequenceGenerator(name = "building_sequence_gen", sequenceName = "building_sequence")
    @Column(name = "building_id")
    private long id;

    private LocalDateTime buildYear; // Null 이 될 가능성이 있음

    @NotEmpty
    private String address;

    @NotNull
    private float exclusivePrivateArea;

    @NotNull
    private int areaCode;

    @OneToMany(mappedBy = "building", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BuildingDeal> beadingDeals;

    @Builder
    public Building(
            Long id,
            LocalDateTime buildYear,
            String address,
            float exclusivePrivateArea,
            int areaCode) {
        this.id = id;
        this.buildYear = buildYear;
        this.address = address;
        this.exclusivePrivateArea = exclusivePrivateArea;
        this.areaCode = areaCode;
    }
}
