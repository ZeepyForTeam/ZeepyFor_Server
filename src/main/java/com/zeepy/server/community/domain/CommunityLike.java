package com.zeepy.server.community.domain;

import com.zeepy.server.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class CommunityLike {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_sequence_gen")
    @SequenceGenerator(name = "like_sequence_gen", sequenceName = "like_sequence")
    @Column(name = "communitylike_id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public CommunityLike(Long id, Community community, User user) {
        this.id = id;
        this.community = community;
        this.user = user;
    }
}
