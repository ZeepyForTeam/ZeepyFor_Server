package com.zeepy.server.review.domain;

/**
 * Created by KimGyeong 4/19/20.
 */
public enum LessorAge {
	TEN(10), TWENTY(20), THIRTY(30), FOURTY(40), FIFTY(50), SIXTY(60), UNKNOWN(0);

	private int age;

	LessorAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}
}
