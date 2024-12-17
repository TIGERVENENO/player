package ru.tigran.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tigran.player.model.CategoryEntity;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByName(String name);
}
