package ru.tigran.player.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.tigran.player.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
} 