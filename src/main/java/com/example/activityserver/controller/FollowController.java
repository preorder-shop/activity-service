package com.example.activityserver.controller;

import com.example.activityserver.common.response.BaseResponse;
import com.example.activityserver.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/activity/follow")
@RestController
public class FollowController {

    private final FollowService followService;

    @GetMapping("/{followId}")
    public BaseResponse<String> followOther(@PathVariable(name = "followId") String followId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        log.info("현재 팔로우를 요청한 사람 : {} ", userId);
        log.info("팔로우 신청을 받은 사람 : {} ", followId);

        String result = followService.followOtherUser(userId, followId);

        return new BaseResponse<>(result);
    }
}
