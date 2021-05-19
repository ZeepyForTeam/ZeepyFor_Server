package com.zeepy.server.community.dto;

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
        this.id = participation.getCommunity().getId();
        this.communityCategory = participation.getCommunity().getCommunityCategory();
        this.title = participation.getCommunity().getTitle();
        this.content = participation.getCommunity().getContent();
    }
}
