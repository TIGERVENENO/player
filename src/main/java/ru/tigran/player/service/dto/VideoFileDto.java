package ru.tigran.player.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tigran.player.model.CategoryEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoFileDto {
    private String id;
    private String name;
}
