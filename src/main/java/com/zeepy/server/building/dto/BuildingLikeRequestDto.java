package com.zeepy.server.building.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @NotNull(message = "buildingId cannot be Null")
    private Long buildingId;

    public BuildingLikeRequestDto(
        Long buildingId
    ) {
        this.buildingId = buildingId;
    }

    public BuildingLike returnBuildingLikeEntity() {
        return new BuildingLike(
            null,
                LocalDateTime.now()
        );
    }
}
