package ru.tigran.player.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tigran.player.service.dto.AnimalDto;

import java.util.List;

/**
 * Api для животных.
 */
@RequestMapping("/api/animal")
public interface AnimalApi {
    /**
     * Получить всех животных по id категории.
     */
    @GetMapping
    ResponseEntity<List<AnimalDto>> getAnimalesByCategoryId(@RequestParam Integer category);

    /**
     * Получить животного по id.
     */
    @GetMapping("/{id}")
    ResponseEntity<AnimalDto> getAnimalesById(@PathVariable Integer id);
}
