package ru.tigran.player.service.dto;

import lombok.Data;
import java.util.List;

@Data
public class CategoryDto {
    private Integer id;                // Идентификатор категории
    private String name;               // Название категории
    private String imageUrl;           // Ссылка на изображение категории
}
