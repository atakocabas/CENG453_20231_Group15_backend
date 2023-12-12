package com.catan.app.controller;

import com.catan.app.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class PlayerController {
    PlayerService playerService;

    @Autowired
    PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<HttpStatus> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return playerService.login(username, password);
    }

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<HttpStatus> register(@RequestParam("username") String username,
                             @RequestParam("password") String password, @RequestParam("email") String email){
        return ResponseEntity.ok(playerService.register(username, password, email));
    }

    @PutMapping("/resetPassword")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Boolean> resetPassword(@RequestParam("username") String username){
        return ResponseEntity.ok(playerService.resetPassword(username));
    }

}
