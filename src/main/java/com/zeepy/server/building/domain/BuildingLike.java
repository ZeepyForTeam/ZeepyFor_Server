package com.zeepy.server.building.domain;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.zeepy.server.common.domain.BaseTimeEntity;
import com.zeepy.server.user.domain.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Minky on 2021-06-02
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class BuildingLike extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "building_like_sequence_gen")
	@SequenceGenerator(name = "building_like_sequence_gen", sequenceName = "building_like_sequence")
	private Long id;

	@NotNull
	private LocalDateTime likeDate;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "building_id")
	private Building building;

	@Builder
	public BuildingLike(
		Long id,
		LocalDateTime likeDate
	) {
		this.id = id;
		this.likeDate = likeDate;
	}

	public void update() {
		this.likeDate = LocalDateTime.now();
	}
}
