package com.unigo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unigo.dto.response.UsersResponse;
import com.unigo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("users")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UsersResponse getUsers() {
        return userService.getUsers();
    }

}
