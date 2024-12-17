package ru.tigran.player.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tigran.player.api.HeroApi;
import ru.tigran.player.service.HeroService;
import ru.tigran.player.service.dto.HeroDto;

import java.util.List;

@RestController
public class HeroController implements HeroApi {

    private final HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @Override
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<HeroDto>> getHeroesByCategoryId(@RequestParam Integer category) {
        return ResponseEntity.ok(heroService.getHeroesByCategoryId(category));
    }

    @Override
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<HeroDto> getHeroesById(@PathVariable Integer id) {
        return ResponseEntity.ok(heroService.getHeroesById(id));
    };
}
