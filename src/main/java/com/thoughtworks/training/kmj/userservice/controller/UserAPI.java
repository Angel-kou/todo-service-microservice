package com.thoughtworks.training.kmj.userservice.controller;


import com.thoughtworks.training.kmj.userservice.model.User;
import com.thoughtworks.training.kmj.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserAPI {

    private UserService userService;

    private String name;


    public UserAPI(UserService userService,     @Value("${spring.application.name}") String name) {
        this.userService = userService;
        this.name = name;
    }

    @PostMapping("/users")
    public ResponseEntity create(@RequestBody User user)  {
        return userService.create(user);
    }

    @GetMapping("/users")
    public List<User> getList()  {
       return userService.find();
    }

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public User login(@RequestBody User user)  {
        return userService.login(user);
    }

    @PostMapping("/verification")
    public ResponseEntity verification(@RequestBody Integer id)  {
        try {
            return ResponseEntity.ok(userService.findUser(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }










}
