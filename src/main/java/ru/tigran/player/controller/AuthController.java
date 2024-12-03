package ru.tigran.player.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tigran.player.model.UserEntity;
import ru.tigran.player.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody UserEntity user) {
        user.setPassword(userService.encodePassword(user.getPassword()));
        return ResponseEntity.ok(userService.registerUser(user));
    }
} 