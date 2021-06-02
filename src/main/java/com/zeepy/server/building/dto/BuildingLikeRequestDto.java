package com.zeepy.server.building.dto;

import com.zeepy.server.building.domain.BuildingDeal;
import com.zeepy.server.building.domain.BuildingLike;
import com.zeepy.server.building.domain.DealType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * Created by Minky on 2021-06-02
 */

@NoArgsConstructor
@Setter
@Getter
public class BuildingLikeRequestDto {
    private Long likeDate;
    private Long user;
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
