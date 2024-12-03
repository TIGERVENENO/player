package ru.tigran.player.mapper;

import org.springframework.stereotype.Component;

import ru.tigran.player.model.VideoEntity;
import ru.tigran.player.service.dto.VideoDto;

@Component
public class VideoMapper {

    // Конвертация Video в VideoDto
    public VideoDto toDto(VideoEntity video) {
        return new VideoDto(video.getId(), video.getTitle(), video.getDescription(), video.getCategory(), video.getFileId(), video.getThumbnailUrl());
    }

    // Конвертация VideoDto в Video
    public VideoEntity toEntity(VideoDto videoDto) {
        return new VideoEntity(videoDto.getId(), videoDto.getTitle(), videoDto.getDescription(), videoDto.getCategory(), videoDto.getFileId(), videoDto.getThumbnailUrl());
    }

}
