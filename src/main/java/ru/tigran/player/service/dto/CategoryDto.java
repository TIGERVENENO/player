package ru.tigran.player.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;            // Идентификатор категории
    private String name;        // Название категории
    private String description; // Описание категории
}
