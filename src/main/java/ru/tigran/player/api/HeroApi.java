package ru.tigran.player.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tigran.player.service.dto.CategoryDto;
import ru.tigran.player.service.dto.HeroDto;

import java.util.List;

/**
 * Api для героев.
 */
@RequestMapping("/api/hero")
public interface HeroApi {
    /**
     * Получить всех героев по категории.
     */
    @GetMapping
    ResponseEntity<List<HeroDto>> getHeroesByCategory(@RequestParam String category);
}
