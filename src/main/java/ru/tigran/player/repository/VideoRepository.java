package ru.tigran.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tigran.player.model.HeroEntity;
import ru.tigran.player.model.VideoEntity;

import java.util.List;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
    List<VideoEntity> findByHero(HeroEntity hero);
    VideoEntity findById(Integer id);

}
