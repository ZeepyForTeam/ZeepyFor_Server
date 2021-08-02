package com.zeepy.server.building.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.zeepy.server.building.domain.BuildingLike;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Minky on 2021-06-02
 */

@NoArgsConstructor
@Setter
@Getter
public class BuildingLikeRequestDto {
    @NotNull(message = "likeDate cannot be Null")
    private Long likeDate;

    @NotNull(message = "user cannot be Null")
    private Long user;

    @NotNull(message = "buildingId cannot be Null")
    private Long buildingId;

    public BuildingLikeRequestDto(
        Long likeDate,
        Long user,
        Long buildingId
    ) {
        this.likeDate = likeDate;
        this.user = user;
        this.buildingId = buildingId;
    }

    public BuildingLike returnBuildingLikeEntity() {
        return new BuildingLike(
            null,
            new Timestamp(this.likeDate),
            this.user
        );
    }
}
