package ru.tigran.player.mapper;

import org.springframework.stereotype.Component;
import ru.tigran.player.service.dto.VideoDto;
import ru.tigran.player.model.VideoEntity;

@Component
public class VideoMapper {

    // Конвертация Video в VideoDto
    public VideoDto toDto(VideoEntity video) {
        return new VideoDto(video.getId(), video.getTitle(), video.getDescription(), video.getCategory(), video.getFilePath(), video.getThumbnailUrl());
    }

    // Конвертация VideoDto в Video
    public VideoEntity toEntity(VideoDto videoDto) {
        return new VideoEntity(videoDto.getId(), videoDto.getTitle(), videoDto.getDescription(), videoDto.getCategory(), videoDto.getFilePath(), videoDto.getThumbnailUrl());
    }

}
