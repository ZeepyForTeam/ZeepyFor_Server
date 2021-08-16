package com.zeepy.server.review.domain;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by KimGyeong 4/19/20.
 */
public enum Furniture {
	AIRCONDITIONAL,
	WASHINGMACHINE,
	BED,
	CLOSET,
	DESK,
	REFRIDGERATOR,
	INDUCTION,
	BURNER,
	MICROWAVE;

	public static Furniture of(String furniture) {
		return Furniture.valueOf(furniture);
	}

	public static List<Furniture> listOf(List<String> furnitures) {
		return furnitures
			.stream()
			.map(Furniture::of)
			.collect(Collectors.toList());
	}
}
