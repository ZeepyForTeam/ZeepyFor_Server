package com.zeepy.server.community.repository;

import com.zeepy.server.community.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByUserIdAndCommunityId(Long userId, Long communityId);
}
