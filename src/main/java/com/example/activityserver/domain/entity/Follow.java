package com.example.activityserver.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "follow")
@Entity
public class Follow extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "to_user_id")
    private String toUserId;  // follow 신청 받은 유저 id

    @Column(nullable = false,name = "from_user_id")
    private String fromUserId; // follow 를 신청한 유저 id


    @Builder
    public Follow(String toUserId,String fromUserId){
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
    }
}
