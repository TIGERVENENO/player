package ru.tigran.player.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tigran.player.api.AuthApi;
import ru.tigran.player.model.UserEntity;
import ru.tigran.player.service.UserService;
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
    public ResponseEntity<UserEntity> register(@RequestBody @Valid UserDto userDto) {
        try {
            UserEntity user = userService.registerUser(userDto);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
