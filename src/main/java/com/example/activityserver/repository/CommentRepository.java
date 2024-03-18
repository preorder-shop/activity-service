package com.example.activityserver.repository;

import com.example.activityserver.domain.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByUserId(String userId);
}
