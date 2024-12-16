package ru.tigran.player.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tigran.player.api.AuthApi;
import ru.tigran.player.service.UserService;
import ru.tigran.player.service.dto.LoginDto;
import ru.tigran.player.service.dto.UserDto;

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
            LoginDto loginDto = new LoginDto();
            loginDto.setUsername(userDto.getUsername());
            loginDto.setPassword(userDto.getPassword());
            String token = userService.loginUser(loginDto); // Генерация токена после регистрации
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> login(@RequestBody @Valid LoginDto loginDto) {
        try {
            String token = userService.loginUser(loginDto);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body("Неверный логин или пароль");
        }
    }
}
