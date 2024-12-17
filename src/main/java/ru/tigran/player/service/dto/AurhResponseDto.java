package ru.tigran.player.service.dto;

import lombok.Data;

@Data
public class AurhResponseDto {
    private String username;
    private String token;
    private String role;
}
