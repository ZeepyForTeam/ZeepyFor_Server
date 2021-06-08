package com.zeepy.server.common.domain;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;

/**
 * Created by KimGyeong 4/19/20.
 */

@Getter
@MappedSuperclass
public abstract class BaseTimeEntity {
	@CreatedDate
	private LocalDateTime createdDate;
}
