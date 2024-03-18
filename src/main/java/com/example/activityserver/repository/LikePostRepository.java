package com.example.activityserver.repository;

import com.example.activityserver.domain.entity.LikePost;
import com.example.activityserver.domain.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost,Long> {

    Optional<LikePost> findByUserIdAndPost(String userEmail, Post post);

    List<LikePost> findAllByUserId(String userId);
}
