package com.example.activityserver.repository;

import com.example.activityserver.domain.entity.Comment;
import com.example.activityserver.domain.entity.LikeComment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment,Long> {

    Optional<LikeComment> findByUserIdAndComment(String userId, Comment comment);

    List<LikeComment> findAllByUserId(String userId);

}
