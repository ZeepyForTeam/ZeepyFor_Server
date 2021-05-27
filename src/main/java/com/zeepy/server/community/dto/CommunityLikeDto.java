package com.zeepy.server.community.dto;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityLike;
import com.zeepy.server.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CommunityLikeDto {
    private User user;
    private Community community;

    public CommunityLike toEntity() {
        return CommunityLike.builder()
                .id(null)
                .community(community)
                .user(user)
                .build();
    }
}
