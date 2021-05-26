package com.zeepy.server.user.domain;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityLike;
import com.zeepy.server.community.domain.Participation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "user")
    private List<CommunityLike> likedCommunities = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Participation> participatingCommunities = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Community> communities = new ArrayList<>();

    @Builder
    public User(String name) {
        this.name = name;
    }
}
