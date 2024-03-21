package com.example.activityserver.controller;

import static com.example.activityserver.common.response.BaseResponseStatus.COMMENT_EMPTY;

import com.example.activityserver.common.exceptions.BaseException;
import com.example.activityserver.common.response.BaseResponse;
import com.example.activityserver.domain.dto.request.CreateCommentReq;
import com.example.activityserver.domain.dto.response.CommentDto;
import com.example.activityserver.domain.dto.response.CreateCommentRes;
import com.example.activityserver.service.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/activity/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성 API
     */
    @PostMapping("/{id}")
    public BaseResponse<CreateCommentRes> createComment(@RequestBody CreateCommentReq createCommentReq, @PathVariable("id") Long id){

        checkCommentValidation(createCommentReq.getContent());

        String userId = getUserIdFromAuthentication();
        CreateCommentRes createCommentRes = commentService.createComment(userId, createCommentReq, id);

        return new BaseResponse<>(createCommentRes);
    }


    /**
     * 내가 작성한 댓글 조회 API
     */
    @GetMapping("")
    public BaseResponse<List<CommentDto>> getMyCommentList(){

        String userId = getUserIdFromAuthentication();
        List<CommentDto> result = commentService.getMyCommentList(userId);

        return new BaseResponse<>(result);
    }


    /**
     * 댓글에 좋아요 API
     */
    @GetMapping("/like/{id}")
    public BaseResponse<String> likeComment(@PathVariable("id") Long id){

        String userId = getUserIdFromAuthentication();
        String result = commentService.likeComment(userId, id);

        return new BaseResponse<>(result);
    }


    /**
     * 내가 좋아요한 댓글 조회 API
     */
    @GetMapping("/like")
    public BaseResponse<List<CommentDto>> getMyLikeCommentList(){

        String userId = getUserIdFromAuthentication();
        List<CommentDto> result = commentService.getMyLikeCommentList(userId);

        return new BaseResponse<>(result);
    }



    private String getUserIdFromAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }


    private void checkCommentValidation(String comment){
        if(comment==null || comment.isBlank())
            throw new BaseException(COMMENT_EMPTY);

    }

}
