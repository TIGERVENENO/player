package ru.tigran.player.service.dto;

import lombok.Data;
import java.util.List;

@Data
public class AnimalDto {
    private Integer id;               // Идентификатор животного
    private String name;              // Имя животного
    private String imageUrl;          // Ссылка на изображение животного
    private Integer categoryId;       // Идентификатор категории
}
