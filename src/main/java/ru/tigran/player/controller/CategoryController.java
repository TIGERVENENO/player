package ru.tigran.player.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.tigran.player.api.CategoryApi;
import ru.tigran.player.service.CategoryService;
import ru.tigran.player.service.VideoService;
import ru.tigran.player.service.dto.CategoryDto;

import java.util.List;

@RestController
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<CategoryDto>> getCategory() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
