package ru.tigran.player.service;

import org.springframework.stereotype.Service;
import ru.tigran.player.mapper.HeroMapper;
import ru.tigran.player.model.CategoryEntity;
import ru.tigran.player.model.HeroEntity;
import ru.tigran.player.repository.CategoryRepository;
import ru.tigran.player.repository.HeroRepository;
import ru.tigran.player.service.dto.HeroDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HeroService {

    private final HeroMapper heroMapper;
    private final HeroRepository heroRepository;
    private final CategoryRepository categoryRepository;


    public HeroService(HeroMapper heroMapper, HeroRepository heroRepository, CategoryRepository categoryRepository) {

        this.heroMapper = heroMapper;
        this.heroRepository = heroRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<HeroDto> getHeroesByCategory(String name) {
        CategoryEntity category = categoryRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + name)); // На случай, если категория не найдена
        List<HeroEntity> heroesEntities = heroRepository.findByCategory(category);
        return heroesEntities.stream()
                .map(heroMapper::toDto)
                .collect(Collectors.toList());
    }
}