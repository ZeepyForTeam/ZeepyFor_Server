package com.zeepy.server.community.domain;

import com.zeepy.server.community.repository.ParticipationRepository;
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
    public Participation(Community community, User user){
        this.community = community;
        this.user = user;
    }
}
