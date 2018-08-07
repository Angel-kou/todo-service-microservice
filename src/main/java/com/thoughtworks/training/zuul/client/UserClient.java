package com.thoughtworks.training.zuul.client;

import com.thoughtworks.training.zuul.dto.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user", url = "http://localhost:8081")
public interface UserClient {

    @PostMapping("/verification")
    User verifyTokenIsValid(@RequestBody String token);


    @PostMapping("/login")
    ResponseEntity verifyUserIsExist(@RequestBody User user);

}
