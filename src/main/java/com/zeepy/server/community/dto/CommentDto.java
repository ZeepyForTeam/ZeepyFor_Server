package com.zeepy.server.community.dto;

import com.zeepy.server.community.domain.Comment;
import com.zeepy.server.community.domain.Community;
import com.zeepy.server.user.domain.User;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CommentDto {
    private String comment;
    private Comment superComment;
    private Community community;
    private User writer;

    @Builder
    public CommentDto(String comment, Comment superComment, Community community, User writer) {
        this.comment = comment;
        this.superComment = superComment;
        this.community = community;
        this.writer = writer;
    }

    public Comment toEntity() {
        Comment comment = Comment.builder()
                .comment(this.comment)
                .user(this.writer)
                .build();
        comment.setSuperComment(this.superComment);
        comment.setCommunity(this.community);
        return comment;
    }
}
