package ru.tigran.player.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String role; // ADMIN или USER
}
