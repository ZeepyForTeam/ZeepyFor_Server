package com.zeepy.server.community.domain;

import com.zeepy.server.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Participation(Long id, Community community, User user) {
        this.id = id;
        this.community = community;
        this.user = user;
    }

    public void setCommunity(Community community) {
        if (this.community != null) {
            this.community.getParticipationsList().remove(this);
        }
        this.community = community;
        community.getParticipationsList().add(this);
    }

    public void setUser(User user) {
        if (this.user != null) {
            this.user.getParticipatingCommunities().remove(this);
        }
        this.user = user;
        user.getParticipatingCommunities().add(this);
    }
}
