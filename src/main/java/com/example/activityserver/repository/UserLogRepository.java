package com.example.activityserver.repository;

import com.example.activityserver.domain.ActiveType;
import com.example.activityserver.domain.entity.UserLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog,Long> {

    List<UserLog> findAllByActorIn(List<String> userId);

    List<UserLog> findAllByRecipientIn(List<String> userId);

    List<UserLog> findAllByRecipientAndActiveTypeIn(String userId, List<ActiveType> types);
}
