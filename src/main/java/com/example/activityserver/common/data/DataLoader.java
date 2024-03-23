package com.example.activityserver.common.data;

import com.example.activityserver.domain.entity.Post;
import com.example.activityserver.repository.PostRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {

    private final PostRepository postRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        Post user = Post.builder()
                .userId(UUID.randomUUID().toString())
                .title("post sample")
                .content("This is first post in this web")
                .build();
        postRepository.save(user);

        log.info("post 데이터 초기화");
    }
}
