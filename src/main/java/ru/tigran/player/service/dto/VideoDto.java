package ru.tigran.player.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tigran.player.model.CategoryEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {
    private Long id;                    // Идентификатор видео
    private String title;               // Заголовок видео
    private String description;         // Описание видео
    @JsonIgnore
    private CategoryEntity category;    // Категория видео
    private String filePath;            // Путь до файла
    private String thumbnailUrl;        // URL превью-изображения
}
