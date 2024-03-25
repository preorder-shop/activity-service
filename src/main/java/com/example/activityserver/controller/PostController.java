package com.example.activityserver.controller;

import static com.example.activityserver.common.response.BaseResponseStatus.POST_EMPTY_CONTENT;
import static com.example.activityserver.common.response.BaseResponseStatus.POST_EMPTY_TITLE;

import com.example.activityserver.common.exceptions.BaseException;
import com.example.activityserver.common.response.BaseResponse;
import com.example.activityserver.domain.dto.request.CreatePostReq;
import com.example.activityserver.domain.dto.response.CreatePostRes;
import com.example.activityserver.domain.dto.response.PostDto;
import com.example.activityserver.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/activity/posts")
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("")
    public BaseResponse<CreatePostRes> createPost(@RequestBody CreatePostReq createPostReq){

        checkTitleValidation(createPostReq.getTitle());
        checkContentValidation(createPostReq.getContent());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        CreatePostRes createPostRes = postService.createPost(userId , createPostReq);

        return new BaseResponse<>(createPostRes);
    }

    @GetMapping("")
    public BaseResponse<List<PostDto>> getMyPostList(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        List<PostDto> result = postService.getMyPostList(userId);
        return new BaseResponse<>(result);
    }


    @GetMapping ("/like/{id}")
    public BaseResponse<String> likePost(@PathVariable("id") Long id){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        String result = postService.likePost(userId, id);

        return new BaseResponse<>(result);
    }

    /**
     * 내가 좋아요한 포스트 목록 조회 API
     */
    @GetMapping("/like")
    public BaseResponse<List<PostDto>> getMyLikePostList(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        List<PostDto> result = postService.getMyLikePostList(userId);
        return new BaseResponse<>(result);

    }



    private void checkTitleValidation(String title){
        if(title==null || title.isBlank()){
            throw new BaseException(POST_EMPTY_TITLE);
        }

    }

    private void checkContentValidation(String content){
        if(content==null || content.isBlank()){
            throw new BaseException(POST_EMPTY_CONTENT);
        }
    }


}
