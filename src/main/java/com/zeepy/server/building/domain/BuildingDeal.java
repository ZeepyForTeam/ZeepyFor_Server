package com.zeepy.server.building.domain;

import com.zeepy.server.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
@Setter
@Entity
public class BuildingDeal extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "building_deal_sequence_gen")
    @SequenceGenerator(name = "building_deal_sequence_gen", sequenceName = "building_deal_sequence")
    private Long id;

    @NotNull
    private LocalDateTime dealDate;

    @NotNull
    private int deposit;

    @NotNull
    private int monthlyRent;

    @NotNull
    private int floor;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "building_id")
    private Building building;

    @Builder
    public BuildingDeal(
            Long id,
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
}
