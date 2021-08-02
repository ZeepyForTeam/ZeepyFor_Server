package com.zeepy.server.building.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.zeepy.server.building.dto.BuildingLikeRequestDto;
import com.zeepy.server.common.domain.BaseTimeEntity;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "building_like_sequence_gen")
    @SequenceGenerator(name = "building_like_sequence_gen", sequenceName = "building_like_sequence")
    private Long id;

    @NotNull
    private Timestamp likeDate;

    @Deprecated
    private Long user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "building_id")
    private Building building;

    @Builder
    public BuildingLike(
        Long id,
        Timestamp likeDate,
        Long user
    ) {
        this.id = id;
        this.likeDate = likeDate;
        this.user = user;
    }

    public void update(BuildingLikeRequestDto buildingLikeRequestDto) {
        this.likeDate = new Timestamp(buildingLikeRequestDto.getLikeDate());
    }
}
