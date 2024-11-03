package ru.tigran.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tigran.player.model.VideoEntity;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
    // Метод для поиска видео по названию
    List<VideoEntity> findByTitleContainingIgnoreCase(String title);

    // Метод для получения видео по категории
    List<VideoEntity> findByCategory_Id(Long categoryId);
}
