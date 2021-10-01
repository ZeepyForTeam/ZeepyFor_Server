package com.zeepy.server.building.domain;

import com.zeepy.server.common.domain.BaseTimeEntity;
import com.zeepy.server.user.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
