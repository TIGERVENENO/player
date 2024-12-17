package ru.tigran.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tigran.player.model.CategoryEntity;
import ru.tigran.player.model.HeroEntity;

import java.util.List;

public interface HeroRepository extends JpaRepository<HeroEntity, Integer> {
    List<HeroEntity> findByCategory(CategoryEntity category);
    HeroEntity findByName(String name);
}
