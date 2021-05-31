package com.zeepy.server.community.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class CommunityResponseDtos {
    private List<CommunityResponseDto> communityResponseDtoList;

    public CommunityResponseDtos(List<CommunityResponseDto> communityResponseDtoList) {
        this.communityResponseDtoList = communityResponseDtoList;
    }
}
