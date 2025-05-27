package ru.tigran.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tigran.player.model.CategoryEntity;
import ru.tigran.player.model.AnimalEntity;

import java.util.List;

public interface AnimalRepository extends JpaRepository<AnimalEntity, Long> {
    List<AnimalEntity> findByCategory(CategoryEntity category);
    AnimalEntity findByName(String name);
    AnimalEntity findById(Integer id);
}
