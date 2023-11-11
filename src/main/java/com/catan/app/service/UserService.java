package com.catan.app.service;

import com.catan.app.entity.User;
import com.catan.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MailService mailService;

    public Boolean register(String username, String password, String email) {
        if(Boolean.TRUE.equals(findUserByUsername(username))) {
            throw new IllegalStateException("Username already taken!");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        // TODO: hash password
        user.setPassword(password);
        userRepository.save(user);
        return Boolean.TRUE;
    }

    public User findUserByUsernameAndPassword(String username, String password) {
        return userRepository.findUserByUsernameAndPassword(username, password);
    }

    public ResponseEntity<HttpStatus> login(String username, String password) {
        User user = findUserByUsernameAndPassword(username, password);
        if (user != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Boolean findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return user != null;
    }

    public Boolean resetPassword(String username) {
        User user = userRepository.findUserByUsername(username);
        if(user == null) {
            throw new IllegalStateException("User does not exist!");
        }
        // TODO: create new password
        String newPassword = "";
        userRepository.updatePassword(username, newPassword);
        mailService.sendMail(user, newPassword);
        return true;
    }

}
