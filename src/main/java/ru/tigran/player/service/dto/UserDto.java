package ru.tigran.player.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    private String role;
}