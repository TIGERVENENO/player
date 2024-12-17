package ru.tigran.player.service.dto;

import lombok.Data;

@Data
public class VideoDto {
    private Integer id;              // Идентификатор видео
    private String name;             // Название видео
    private String imageUrl;         // Ссылка на изображение видео
    private Integer heroId;          // Идентификатор героя
}
