package com.thoughtworks.training.zuul.client;

import com.thoughtworks.training.zuul.dto.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("user-service")
public interface UserClient {

    @PostMapping("/verification")
    User verifyTokenIsValid(@RequestBody Integer id);


    @PostMapping("/login")
    User verifyUserIsExist(@RequestBody User user);

}
