package ru.tigran.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tigran.player.mapper.HeroMapper;
import ru.tigran.player.model.CategoryEntity;
import ru.tigran.player.model.HeroEntity;
import ru.tigran.player.repository.CategoryRepository;
import ru.tigran.player.repository.HeroRepository;
import ru.tigran.player.service.HeroService;
import ru.tigran.player.service.dto.HeroDto;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HeroServiceTest {

    @Mock
    private HeroMapper heroMapper;

    @Mock
    private HeroRepository heroRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private HeroService heroService;

    private CategoryEntity category;
    private HeroEntity hero;
    private HeroDto heroDto;

    @BeforeEach
    void setUp() {
        category = new CategoryEntity();
        category.setId(1);
        category.setName("Warrior");

        hero = new HeroEntity();
        hero.setId(1);
        hero.setName("Conan");
        hero.setImageUrl("http://example.com/conan.jpg");
        hero.setCategory(category);

        heroDto = new HeroDto();
        heroDto.setId(1);
        heroDto.setName("Conan");
        heroDto.setImageUrl("http://example.com/conan.jpg");
        heroDto.setCategoryId(1);
    }

    @Test
    void testGetHeroesByCategoryId() {
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(heroRepository.findByCategory(category)).thenReturn(List.of(hero));
        when(heroMapper.toDto(hero)).thenReturn(heroDto);

        List<HeroDto> result = heroService.getHeroesByCategoryId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Conan", result.get(0).getName());
        verify(categoryRepository).findById(1);
        verify(heroRepository).findByCategory(category);
        verify(heroMapper).toDto(hero);
    }

    @Test
    void testGetHeroesByCategoryId_CategoryNotFound() {
        when(categoryRepository.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            heroService.getHeroesByCategoryId(2);
        });

        assertEquals("Category not found: 2", exception.getMessage());
        verify(categoryRepository).findById(2);
        verifyNoInteractions(heroRepository, heroMapper);
    }

    @Test
    void testGetHeroesById() {
        when(heroRepository.findById(1)).thenReturn(hero);
        when(heroMapper.toDto(hero)).thenReturn(heroDto);

        HeroDto result = heroService.getHeroesById(1);

        assertNotNull(result);
        assertEquals("Conan", result.getName());
        verify(heroRepository).findById(1);
        verify(heroMapper).toDto(hero);
    }
}