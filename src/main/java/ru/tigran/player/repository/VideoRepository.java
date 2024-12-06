package ru.tigran.player.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tigran.player.model.CategoryEntity;
import ru.tigran.player.model.VideoEntity;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
    Page<VideoEntity> findByCategory(CategoryEntity category, Pageable pageable);
}
