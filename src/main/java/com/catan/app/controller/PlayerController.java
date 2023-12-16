package com.catan.app.controller;

import com.catan.app.dto.LoginRequest;
import com.catan.app.dto.RegisterRequest;
import com.catan.app.dto.ResetPasswordRequest;
import com.catan.app.service.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class PlayerController {
    PlayerService playerService;

    @Autowired
    PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> login(@Validated @RequestBody LoginRequest request) {
        return playerService.login(request.username, request.password);
    }

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<HttpStatus> register(@Validated @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(playerService.register(request.username, request.password, request.email));
    }

    @PutMapping("/resetPassword")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<HttpStatus> resetPassword(@Validated @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(playerService.resetPassword(request.username));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationExceptions(MethodArgumentNotValidException ex) {
        return "Bad Request";
    }
}
