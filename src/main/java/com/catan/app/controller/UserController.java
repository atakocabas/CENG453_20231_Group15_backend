package com.catan.app.controller;

import com.catan.app.entity.User;
import com.catan.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<HttpStatus> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userService.login(username, password);
    }

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void register(@RequestBody String username, @RequestBody String password, @RequestBody String email){
        userService.register(username, password, email);
    }

}
