package com.example.activityserver.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CreateCommentRes {

    private Long id;
    private Long postId;
    private String comment;
    private String writer;

    @Builder
    public CreateCommentRes(Long id,Long postId,String comment,String writer){
        this.id = id;
        this.postId = postId;
        this.comment = comment;
        this.writer = writer;

    }

}
