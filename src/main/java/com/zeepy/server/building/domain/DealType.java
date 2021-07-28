package com.zeepy.server.building.domain;

/**
 * Created by Minky on 2021-06-02
 */

public enum DealType {
	MONTHLY, // 월세
	JEONSE, // 전세
	DEAL; // 매매

	public static DealType getDealType(int dealCost, int monthlyRent) {
		if (dealCost != 0) {
			return DEAL;
		}
		if (monthlyRent == 0) {
			return JEONSE;
		}
		return MONTHLY;
	}
}
