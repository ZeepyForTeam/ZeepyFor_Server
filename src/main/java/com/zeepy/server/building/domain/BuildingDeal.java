package com.zeepy.server.building.domain;

import com.zeepy.server.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by Minky on 2021-05-15
 */

/**
 * DB 구조
 * 거래 날짜
 * 보증 금액
 * 월세 금액
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BuildingDeal extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "building_deal_sequence_gen")
    @SequenceGenerator(name = "building_deal_sequence_gen", sequenceName = "building_deal_sequence")
    private long id;

    @NotNull
    private LocalDateTime dealDate;

    @NotNull
    private int deposit;

    @NotNull
    private int monthlyRent;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "building_id")
    private Building building;
}
