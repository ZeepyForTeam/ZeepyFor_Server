package com.zeepy.server.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JoinCommunityRequestDto {
    @NotBlank
    private String comment;

    private Long participationUserId;
}
