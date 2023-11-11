package com.catan.app.controller;

import com.catan.app.entity.User;
import com.catan.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public User login(@RequestParam String username, @RequestParam String password) {
        return userService.findUserByUsernameAndPassword(username, password);
    }
}
