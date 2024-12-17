package ru.tigran.player.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}
