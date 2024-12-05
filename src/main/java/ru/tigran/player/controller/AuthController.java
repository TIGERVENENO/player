package ru.tigran.player.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tigran.player.model.UserEntity;
import ru.tigran.player.service.UserService;
import ru.tigran.player.service.dto.UserDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody @Valid UserDto userDto) {
        try {
            UserEntity user = userService.registerUser(userDto);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
