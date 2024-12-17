package ru.tigran.player.mapper;

import org.springframework.stereotype.Component;
import ru.tigran.player.model.VideoEntity;
import ru.tigran.player.service.dto.VideoDto;

@Component
public class VideoMapper {

    // Конвертация VideoEntity в VideoDto
    public VideoDto toDto(VideoEntity video) {
        VideoDto dto = new VideoDto();
        dto.setId(video.getId());
        dto.setName(video.getName());
        dto.setImageUrl(video.getImageUrl());
        dto.setHeroId(video.getHero() != null ? video.getHero().getId() : null);
        return dto;
    }

    // Конвертация VideoDto в VideoEntity
    public VideoEntity toEntity(VideoDto videoDto) {
        VideoEntity entity = new VideoEntity();
        entity.setId(videoDto.getId());
        entity.setName(videoDto.getName());
        entity.setImageUrl(videoDto.getImageUrl());
        // Hero привязывается отдельно в сервисе
        return entity;
    }
}
