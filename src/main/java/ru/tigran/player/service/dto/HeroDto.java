package ru.tigran.player.service.dto;

import lombok.Data;
import java.util.List;

@Data
public class HeroDto {
    private Integer id;               // Идентификатор героя
    private String name;              // Имя героя
    private String imageUrl;          // Ссылка на изображение героя
    private Integer categoryId;       // Идентификатор категории
}
