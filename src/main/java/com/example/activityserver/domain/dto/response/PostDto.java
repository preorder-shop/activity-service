package com.example.activityserver.domain.dto.response;

import com.example.activityserver.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class PostDto {

    private Long postId;
    private String writer; // 글 쓴 사람
    private String title;
    private String content;

    @Builder
    private PostDto(Long postId, String writer, String title, String content){
        this.postId = postId;
        this.writer = writer;
        this.title = title;
        this.content = content;

    }

    public static PostDto of(Post post){
        return PostDto.builder()
                .postId(post.getId())
                .writer(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }


}
