package ru.tigran.player.mapper;

import org.springframework.stereotype.Component;
import ru.tigran.player.model.CategoryEntity;
import ru.tigran.player.service.dto.CategoryDto;

@Component
public class CategoryMapper {

    private final AnimalMapper animalMapper;

    public CategoryMapper(AnimalMapper animalMapper) {
        this.animalMapper = animalMapper;
    }

    // Конвертация CategoryEntity в CategoryDto
    public CategoryDto toDto(CategoryEntity category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setImageUrl(category.getImageUrl());
        return dto;
    }

    // Конвертация CategoryDto в CategoryEntity
    public CategoryEntity toEntity(CategoryDto categoryDto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(categoryDto.getId());
        entity.setName(categoryDto.getName());
        entity.setImageUrl(categoryDto.getImageUrl());
        return entity;
    }
}
