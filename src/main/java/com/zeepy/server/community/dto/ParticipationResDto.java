package com.zeepy.server.community.dto;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;
import com.zeepy.server.community.domain.Participation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParticipationResDto {
    private Long id;
    private CommunityCategory communityCategory;
    private String title;
    private String content;

    @Builder
    public ParticipationResDto(Participation participation) {
        Community thisCommunity = participation.getCommunity();
        this.id = thisCommunity.getId();
        this.communityCategory = thisCommunity.getCommunityCategory();
        this.title = thisCommunity.getTitle();
        this.content = thisCommunity.getContent();
    }
}
