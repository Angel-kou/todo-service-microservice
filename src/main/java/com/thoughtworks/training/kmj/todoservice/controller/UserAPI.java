package com.thoughtworks.training.kmj.todoservice.controller;


import com.thoughtworks.training.kmj.todoservice.model.User;
import com.thoughtworks.training.kmj.todoservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserAPI {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity create(@RequestBody User user)  {
        return userService.create(user);
    }

    @GetMapping("/users")
    public List<User> getList()  {
       return userService.find();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user)  {
        return userService.login(user);
    }


}
