package com.zeepy.server.community.dto;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.Participation;
import com.zeepy.server.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ParticipationDto {
    private Community community;
    private User user;

    @Builder
    public Participation toEntity(){
        Participation participation = Participation.builder()
                .build();
        participation.setCommunity(this.community);
        participation.setUser(this.user);
        return participation;
    }
}
