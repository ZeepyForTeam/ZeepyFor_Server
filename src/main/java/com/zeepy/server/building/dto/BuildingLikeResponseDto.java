package com.zeepy.server.building.dto;

import com.zeepy.server.building.domain.BuildingDeal;
import com.zeepy.server.building.domain.BuildingLike;
import com.zeepy.server.building.domain.DealType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Minky on 2021-06-02
 */

@NoArgsConstructor
@Setter
@Getter
public class BuildingLikeResponseDto {
    private Long id;
    private Timestamp likeDate;
    private Long user;

    public BuildingLikeResponseDto(
            Long id,
            Timestamp likeDate,
            Long user
    ) {
        this.id = id;
        this.likeDate = likeDate;
        this.user = user;
    }

    public static BuildingLikeResponseDto of(BuildingLike buildingLike) {
        return new BuildingLikeResponseDto(
                buildingLike.getId(),
                buildingLike.getLikeDate(),
                buildingLike.getUser()
        );
    }

    public static List<BuildingLikeResponseDto> listOf(List<BuildingLike> buildingLikeList) {
        return buildingLikeList
                .stream()
                .map(BuildingLikeResponseDto::of)
                .collect(Collectors.toList());
    }
}
