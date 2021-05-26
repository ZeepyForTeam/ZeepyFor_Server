package com.zeepy.server.community.domain;

import com.zeepy.server.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne
    @JoinColumn(name = "super_comment_id")
    private Comment superComment;

    @OneToMany(mappedBy = "superComment")
    private List<Comment> subComments = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Comment(Long id, String comment, Community community, User user) {
        this.id = id;
        this.comment = comment;
        this.community = community;
        this.user = user;
    }

    public void setSuperComment(Comment comment) {
        this.superComment = comment;
        if (comment != null) {
            comment.getSubComments().add(this);
        }
    }

    public void setCommunity(Community community) {
        this.community = community;
        if (this.superComment == null) {
            community.getComments().add(this);
        }
    }
}
