package ru.tigran.player.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.tigran.player.service.dto.AurhResponseDto;
import ru.tigran.player.service.dto.LoginDto;
import ru.tigran.player.service.dto.UserDto;

/**
 * Api для регистрации.
 */
@RequestMapping("/api/auth")
public interface AuthApi {
    /**
     * Регистрация.
     */
    @PostMapping("/register")
    ResponseEntity<AurhResponseDto> register(@RequestBody @Valid UserDto userDto);

    /**
     * Вход.
     */
    @PostMapping("/login")
    ResponseEntity<AurhResponseDto> login(@RequestBody @Valid LoginDto loginDto);
}
