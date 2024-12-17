package ru.tigran.player.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.tigran.player.service.dto.CategoryDto;

import java.util.List;

/**
 * Api для категорий.
 */
@RequestMapping("/api/category")
public interface CategoryApi {
    /**
     * Получить все категории.
     */
    @GetMapping
    ResponseEntity<List<CategoryDto>> getCategory();
}
