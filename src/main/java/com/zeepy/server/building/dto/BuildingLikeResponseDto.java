package com.zeepy.server.building.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.zeepy.server.building.domain.BuildingLike;

import com.zeepy.server.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Minky on 2021-06-02
 */

@NoArgsConstructor
@Setter
@Getter
public class BuildingLikeResponseDto {
    private Long id;
    private LocalDateTime likeDate;
    private String email;

    public BuildingLikeResponseDto(
            Long id,
            LocalDateTime likeDate,
            String email
    ) {
        this.id = id;
        this.likeDate = likeDate;
        this.email = email;
    }

    public static BuildingLikeResponseDto of(BuildingLike buildingLike) {
        return new BuildingLikeResponseDto(
                buildingLike.getId(),
                buildingLike.getLikeDate(),
                buildingLike.getUser().getEmail()
        );
    }

    public static List<BuildingLikeResponseDto> listOf(List<BuildingLike> buildingLikeList) {
        return buildingLikeList
                .stream()
                .map(BuildingLikeResponseDto::of)
                .collect(Collectors.toList());
    }
}
