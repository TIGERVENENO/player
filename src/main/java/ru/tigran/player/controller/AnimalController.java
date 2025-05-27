package ru.tigran.player.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tigran.player.api.AnimalApi;
import ru.tigran.player.service.AnimalService;
import ru.tigran.player.service.dto.AnimalDto;

import java.util.List;

@RestController
public class AnimalController implements AnimalApi {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @Override
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<AnimalDto>> getAnimalesByCategoryId(@RequestParam Integer category) {
        return ResponseEntity.ok(animalService.getAnimalesByCategoryId(category));
    }

    @Override
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<AnimalDto> getAnimalesById(@PathVariable Integer id) {
        return ResponseEntity.ok(animalService.getAnimalesById(id));
    };
}
