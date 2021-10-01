package com.zeepy.server.building.domain;

import com.zeepy.server.building.dto.BuildingDealRequestDto;
import com.zeepy.server.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Created by Minky on 2021-05-15
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class BuildingDeal extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "building_deal_sequence_gen")
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
