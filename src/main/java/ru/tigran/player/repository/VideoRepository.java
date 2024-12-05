package ru.tigran.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tigran.player.model.VideoEntity;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
}
