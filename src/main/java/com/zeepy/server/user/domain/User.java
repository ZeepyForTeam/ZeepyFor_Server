package com.zeepy.server.user.domain;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityLike;
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
    private List<CommunityLike> likeCommunities = new ArrayList<>();

    @Builder
    public User(String name){
        this.name = name;
    }
}
