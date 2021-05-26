package com.zeepy.server.community.dto;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityLike;
import com.zeepy.server.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LikeRequestDto {

    @NotNull(message = "Dto_communityId value shouldn't be null")
    private Long communityId;

    @NotNull(message = "Dto_userId value shouldn't be null")
    private Long userId;

    @Builder
    public LikeRequestDto(Long communityId, Long userId) {
        this.communityId = communityId;
        this.userId = userId;
    }

}