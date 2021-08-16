package com.zeepy.server.building.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.zeepy.server.building.dto.BuildingDealRequestDto;
import com.zeepy.server.common.domain.BaseTimeEntity;

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
public class BuildingDeal extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "building_deal_sequence_gen")
    @SequenceGenerator(name = "building_deal_sequence_gen", sequenceName = "building_deal_sequence")
    private Long id;

	@NotNull
	private Timestamp dealDate;

	@NotNull
	private int deposit; // 보증금

	@NotNull
	private int monthlyRent; // 월세

	@NotNull
	private int dealCost; // 매매 금액

	@NotNull
	private int floor;

	@NotNull
	@Enumerated(EnumType.STRING)
	private DealType dealType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "building_id")
	private Building building;

	@Builder
	public BuildingDeal(
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

	public void update(BuildingDealRequestDto buildingDealRequestDto) {
		this.dealDate = new Timestamp(buildingDealRequestDto.getDealDate());
		this.deposit = buildingDealRequestDto.getDeposit();
		this.monthlyRent = buildingDealRequestDto.getMonthlyRent();
		this.dealCost = buildingDealRequestDto.getDealCost();
		this.floor = buildingDealRequestDto.getFloor();
		if (this.monthlyRent == 0) {
			this.dealType = DealType.JEONSE;
		} else {
            this.dealType = DealType.MONTHLY;
        }
    }
}
