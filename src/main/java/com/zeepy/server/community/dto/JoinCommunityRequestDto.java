package com.zeepy.server.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JoinCommunityRequestDto {
    private String comment;

    private Long participationUserId;

    public boolean isCommentExist(){
        return comment != null && !comment.isEmpty();
    }
}
