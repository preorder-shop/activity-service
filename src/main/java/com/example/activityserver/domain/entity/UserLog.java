package com.example.activityserver.domain.entity;

import com.example.activityserver.domain.ActiveType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "user_log")
@Entity
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String actor; // 해당 활동을 한 유저
    @Column(nullable = false)
    private String recipient;  // 특정 행위를 당한 유저
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "active_type")
    private ActiveType activeType;

    @Builder
    public UserLog(String actor, String recipient, ActiveType activeType){
        this.actor = actor;
        this.recipient = recipient;
        this.activeType = activeType;
    }
}
