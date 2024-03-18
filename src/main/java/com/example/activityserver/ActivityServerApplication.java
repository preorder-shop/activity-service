package com.example.activityserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class ActivityServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivityServerApplication.class, args);
    }

}
