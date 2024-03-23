package com.example.activityserver.controller;

import com.example.activityserver.domain.dto.request.GetPostListByConditionReq;
import com.example.activityserver.domain.dto.response.PostDto;
import com.example.activityserver.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/internal/activity")
@RestController
public class InternalController {

    private final PostService postService;


    // 조건에 따른 포스트 리스트 get
    @PostMapping("/post")
    public List<PostDto> getPostListByCondition(@RequestBody GetPostListByConditionReq getPostListByConditionReq){
        log.info("ActivityService InternalController start");
        return postService.getPostListByCondition(getPostListByConditionReq);
    }





}
