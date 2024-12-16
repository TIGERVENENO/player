package ru.tigran.player.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tigran.player.service.dto.UserDto;
import ru.tigran.player.model.Role;
import ru.tigran.player.model.UserEntity;
import ru.tigran.player.repository.UserRepository;

import java.util.Base64;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Регистрация нового пользователя.
     */
    public UserEntity registerUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }

        UserEntity user = new UserEntity();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        try {
            user.setRole(Role.valueOf(userDto.getRole().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Некорректная роль: " + userDto.getRole());
        }

        return userRepository.save(user);
    }

    /**
     * Авторизация пользователя. Возвращает Basic Access Token.
     */
    public String loginUser(UserDto userDto) {
        // Ищем пользователя по имени
        UserEntity user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Неверный логин или пароль"));

        // Проверяем, соответствует ли введённый пароль хэшу
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Неверный логин или пароль");
        }

        // Генерируем Basic токен на основе исходного логина и пароля
        return generateBasicToken(userDto.getUsername(), userDto.getPassword());
    }


    /**
     * Генерация Basic Access токена для аутентификации.
     */
    public String generateBasicToken(String username, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }
}