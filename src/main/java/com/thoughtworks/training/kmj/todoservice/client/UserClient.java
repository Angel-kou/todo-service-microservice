package com.thoughtworks.training.kmj.todoservice.client;

import com.thoughtworks.training.kmj.todoservice.dto.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user", url = "http://localhost:8081")
public interface UserClient {

    @PostMapping("/verification")
    User verifyTokenIsExist(@RequestBody String token);

}
