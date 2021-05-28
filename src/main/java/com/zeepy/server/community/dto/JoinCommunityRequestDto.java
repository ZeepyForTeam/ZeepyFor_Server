package com.zeepy.server.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JoinCommunityRequestDto {
    @NotBlank(message = "빈 댓글은 사용할수 없습니다.")
    private String comment;

    private Long participationUserId;
}
