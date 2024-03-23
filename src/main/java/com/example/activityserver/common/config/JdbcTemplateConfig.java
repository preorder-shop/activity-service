package com.example.activityserver.common.config;

import com.example.activityserver.repository.JdbcRepository;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JdbcTemplateConfig {

    private final DataSource dataSource;

    @Bean
    public JdbcRepository jdbcRepository(){
        return new JdbcRepository(dataSource);
    }
}
