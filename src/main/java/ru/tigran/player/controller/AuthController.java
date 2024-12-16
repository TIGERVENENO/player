package ru.tigran.player.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tigran.player.api.AuthApi;
import ru.tigran.player.model.UserEntity;
import ru.tigran.player.service.UserService;
import ru.tigran.player.service.dto.UserDto;
import ru.tigran.player.config.SecurityConfig;

import java.util.Base64;

/**
 * Контроллер для регистрации.
 */
@RestController
public class AuthController implements AuthApi {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<String> register(@RequestBody @Valid UserDto userDto) {
        try {
            userService.registerUser(userDto);
            String token = userService.loginUser(userDto);  // Генерация токена после регистрации
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserDto userDto) {
        try {
            String token = userService.loginUser(userDto);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body("Неверный логин или пароль");
        }
    }
}
