package ru.tigran.player.mapper;

import org.springframework.stereotype.Component;
import ru.tigran.player.model.HeroEntity;
import ru.tigran.player.service.dto.HeroDto;

import java.util.stream.Collectors;

@Component
public class HeroMapper {

    private final VideoMapper videoMapper;

    public HeroMapper(VideoMapper videoMapper) {
        this.videoMapper = videoMapper;
    }

    // Конвертация HeroEntity в HeroDto
    public HeroDto toDto(HeroEntity hero) {
        HeroDto dto = new HeroDto();
        dto.setId(hero.getId());
        dto.setName(hero.getName());
        dto.setImageUrl(hero.getImageUrl());
        dto.setCategoryId(hero.getCategory() != null ? hero.getCategory().getId() : null);
        return dto;
    }

    // Конвертация HeroDto в HeroEntity
    public HeroEntity toEntity(HeroDto heroDto) {
        HeroEntity entity = new HeroEntity();
        entity.setId(heroDto.getId());
        entity.setName(heroDto.getName());
        entity.setImageUrl(heroDto.getImageUrl());
        // Category привязывается отдельно в сервисе
        return entity;
    }
}
