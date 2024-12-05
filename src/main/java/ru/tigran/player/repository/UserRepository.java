package ru.tigran.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tigran.player.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}