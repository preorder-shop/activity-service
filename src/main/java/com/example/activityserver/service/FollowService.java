package com.example.activityserver.service;

import static com.example.activityserver.common.response.BaseResponseStatus.FOLLOW_INVALID;

import com.example.activityserver.client.UserServiceClient;
import com.example.activityserver.common.exceptions.BaseException;
import com.example.activityserver.domain.ActiveType;
import com.example.activityserver.domain.dto.response.GetFollowerRes;
import com.example.activityserver.domain.entity.Follow;
import com.example.activityserver.domain.entity.UserLog;
import com.example.activityserver.repository.FollowRepository;
import com.example.activityserver.repository.UserLogRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;

    private final UserLogRepository userLogRepository;

    private final UserServiceClient userServiceClient;


    public String followOtherUser(String fromUserId, String toUserId) {

        String message;

        // Id 값으로 to 유저 확인

     //   userServiceClient.validateUserId(toUserId);

        if (Objects.equals(fromUserId, toUserId)) {
            throw new BaseException(FOLLOW_INVALID);
        }

        Optional<Follow> exist = followRepository.findByFromUserIdAndToUserId(fromUserId, toUserId);

        if (exist.isPresent()) { // 이미 팔로우 했으면 취소 처리
            followRepository.delete(exist.get());

            UserLog userLog = UserLog.builder()
                    .actor(fromUserId)
                    .recipient(toUserId)
                    .activeType(ActiveType.CANCEL_FOLLOW)
                    .build();

            userLogRepository.save(userLog);

            message="팔로우를 취소했습니다.";

        } else {
            Follow follow = Follow.builder()
                    .toUserId(toUserId)
                    .fromUserId(fromUserId)
                    .build();

            followRepository.save(follow);

            UserLog userLog = UserLog.builder()
                    .actor(fromUserId)
                    .recipient(toUserId)
                    .activeType(ActiveType.FOLLOW)
                    .build();

            userLogRepository.save(userLog);

            message="해당 사용자를 팔로우했습니다.";

        }

        return message;
    }


    public List<GetFollowerRes> getMyFollowers(String userId){

        List<Follow> follow = followRepository.findAllByToUserId(userId);

        List<GetFollowerRes> users = new ArrayList<>();

        follow.forEach(f-> users.add(new GetFollowerRes(f.getFromUserId())));

        return users;
    }

}
