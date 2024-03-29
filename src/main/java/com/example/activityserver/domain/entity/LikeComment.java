package com.example.activityserver.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 생성자를 통해서 값 변경 목적으로 접근하는 메시지들 차단
@Table(name = "like_comment")
@Entity
public class LikeComment extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "writer")
    private String userId; // UUID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "comment_id")
    private Comment comment;

    @Builder
    public LikeComment(String userId,Comment comment){
        this.userId = userId;
        this.comment = comment;
    }

}
