package ru.tigran.player.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.tigran.player.model.UserEntity;
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
    public ResponseEntity<String> register(@RequestBody @Valid UserDto userDto);

    /**
     * Вход.
     */
    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody @Valid UserDto userDto);
}