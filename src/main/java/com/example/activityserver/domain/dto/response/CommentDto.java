package com.example.activityserver.domain.dto.response;

import com.example.activityserver.domain.entity.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentDto {

    private Long commentId;
    private String writer; // 글 쓴 사람
    private String content;

    @Builder
    private CommentDto(Long commentId, String writer, String content){
        this.commentId = commentId;
        this.writer = writer;
        this.content = content;

    }

    public static CommentDto of(Comment comment){
        return CommentDto.builder()
                .commentId(comment.getId())
                .writer(comment.getUserId())
                .content(comment.getContent())
                .build();
    }


}
