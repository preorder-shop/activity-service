package com.example.activityserver.repository;

import com.example.activityserver.domain.entity.Follow;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow,Long> {

   Optional<Follow> findByFromUserIdAndToUserId(String fromUserId, String toUserId);

//    List<Follow> findAllByFromUser(User user);

    List<Follow> findAllByToUserId(String userId); // 나를 팔로우한 유저 목록

    List<Follow> findAllByFromUserId(String userId); // 내가 팔로우한 유저 목록
}
