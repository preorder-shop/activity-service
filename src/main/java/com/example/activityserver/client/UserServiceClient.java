package com.example.activityserver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user")
public interface UserServiceClient {

    @GetMapping("/users/internal/{userId}")
    void validateUserId(@PathVariable String userId);



}
