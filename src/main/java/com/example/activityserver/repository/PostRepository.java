package com.example.activityserver.repository;

import com.example.activityserver.domain.entity.Post;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

    Slice<Post> findAllBy(Pageable pageable);

    Slice<Post> findAllByUserIdIn(List<String> userIds, Pageable pageable);

    List<Post> findAllByUserId(String userId);

}
