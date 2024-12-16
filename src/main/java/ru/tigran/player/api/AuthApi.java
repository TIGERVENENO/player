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
     * Получить существующий дашборд по его id.
     */
    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody @Valid UserDto userDto);
}
