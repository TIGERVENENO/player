package ru.tigran.player.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.tigran.player.mapper.CategoryMapper;
import ru.tigran.player.model.CategoryEntity;
import ru.tigran.player.repository.CategoryRepository;
import ru.tigran.player.service.dto.CategoryDto;
import ru.tigran.player.service.dto.VideoDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Получить список всех категорий в виде DTO.
     */
    public List<CategoryDto> getAllCategories() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        return categoryEntities.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }
}