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

/**
 * This class represents a controller for handling player requests.
 */
@RestController
@RequestMapping("api/v1/user")
public class PlayerController {
    PlayerService playerService;

    /**
     * @param playerService the player service
     */
    @Autowired
    PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }


    /**
     * Logs a player in.
     *
     * @param request the request containing the username and password
     * @return the JWT token
     */
    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> login(@Validated @RequestBody LoginRequest request) {
        return playerService.login(request.username, request.password);
    }

    /**
     * Registers a player.
     *
     * @param request the request containing the username, password, and email
     * @return 201 if the player was successfully registered
     */
    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<HttpStatus> register(@Validated @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(playerService.register(request.username, request.password, request.email));
    }

    /**
     * Resets a player's password.
     *
     * @param request the request containing the username
     * @return 200 if the password was successfully reset
     */
    @PutMapping("/resetPassword")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<HttpStatus> resetPassword(@Validated @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(playerService.resetPassword(request.username));
    }

    /**
     * Handles validation exceptions.
     *
     * @param ex the exception
     * @return a string indicating that the request was bad
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationExceptions(MethodArgumentNotValidException ex) {
        return "Bad Request";
    }
}
