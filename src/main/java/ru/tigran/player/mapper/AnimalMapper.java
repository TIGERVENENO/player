package ru.tigran.player.mapper;

import org.springframework.stereotype.Component;
import ru.tigran.player.model.AnimalEntity;
import ru.tigran.player.service.dto.AnimalDto;

import java.util.stream.Collectors;

@Component
public class AnimalMapper {

    private final VideoMapper videoMapper;

    public AnimalMapper(VideoMapper videoMapper) {
        this.videoMapper = videoMapper;
    }

    // Конвертация AnimalEntity в AnimalDto
    public AnimalDto toDto(AnimalEntity animal) {
        AnimalDto dto = new AnimalDto();
        dto.setId(animal.getId());
        dto.setName(animal.getName());
        dto.setImageUrl(animal.getImageUrl());
        dto.setCategoryId(animal.getCategory() != null ? animal.getCategory().getId() : null);
        return dto;
    }

    // Конвертация AnimalDto в AnimalEntity
    public AnimalEntity toEntity(AnimalDto animalDto) {
        AnimalEntity entity = new AnimalEntity();
        entity.setId(animalDto.getId());
        entity.setName(animalDto.getName());
        entity.setImageUrl(animalDto.getImageUrl());
        // Category привязывается отдельно в сервисе
        return entity;
    }
}
