package ru.tigran.player.service;

import org.springframework.stereotype.Service;
import ru.tigran.player.mapper.AnimalMapper;
import ru.tigran.player.model.CategoryEntity;
import ru.tigran.player.model.AnimalEntity;
import ru.tigran.player.repository.CategoryRepository;
import ru.tigran.player.repository.AnimalRepository;
import ru.tigran.player.service.dto.AnimalDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalService {

    private final AnimalMapper animalMapper;
    private final AnimalRepository animalRepository;
    private final CategoryRepository categoryRepository;


    public AnimalService(AnimalMapper animalMapper, AnimalRepository animalRepository, CategoryRepository categoryRepository) {

        this.animalMapper = animalMapper;
        this.animalRepository = animalRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<AnimalDto> getAnimalesByCategoryId(Integer categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryId)); // На случай, если категория не найдена
        List<AnimalEntity> animalesEntities = animalRepository.findByCategory(category);
        return animalesEntities.stream()
                .map(animalMapper::toDto)
                .collect(Collectors.toList());
    }

    public AnimalDto getAnimalesById(Integer id) {
        AnimalEntity animalesEntity = animalRepository.findById(id);
        return animalMapper.toDto(animalesEntity);
    }
}