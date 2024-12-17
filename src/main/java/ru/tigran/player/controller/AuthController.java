package ru.tigran.player.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tigran.player.api.AuthApi;
import ru.tigran.player.service.AuthService;
import ru.tigran.player.service.dto.AurhResponseDto;
import ru.tigran.player.service.dto.LoginDto;
import ru.tigran.player.service.dto.UserDto;

/**
 * Контроллер для регистрации.
 */
@RestController
public class AuthController implements AuthApi {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<AurhResponseDto> register(@RequestBody @Valid UserDto userDto) {
        authService.registerUser(userDto);
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(userDto.getUsername());
        loginDto.setPassword(userDto.getPassword());
        AurhResponseDto aurhResponseDto = authService.loginUser(loginDto); // Генерация токена после регистрации
        return ResponseEntity.ok(aurhResponseDto);
    }

    @Override
    public ResponseEntity<AurhResponseDto> login(@RequestBody @Valid LoginDto loginDto) {
        AurhResponseDto aurhResponseDto = authService.loginUser(loginDto);
        return ResponseEntity.ok(aurhResponseDto);
    }
}
