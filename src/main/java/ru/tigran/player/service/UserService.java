package ru.tigran.player.service;

import org.springframework.stereotype.Service;
import ru.tigran.player.service.dto.LoginDto;
import ru.tigran.player.service.dto.UserDto;
import ru.tigran.player.model.Role;
import ru.tigran.player.model.UserEntity;
import ru.tigran.player.repository.UserRepository;

import java.util.Base64;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        user.setPassword(userDto.getPassword());
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
    public String loginUser(LoginDto loginDto) {
        // Ищем пользователя по логину
        UserEntity user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Неверный логин или пароль"));

        // Сравниваем введённый пароль с паролем в базе данных
        if (!loginDto.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("Неверный логин или пароль");
        }

        // Если пароль совпадает, генерируем и возвращаем Basic Access Token
        return generateBasicToken(loginDto.getUsername(), loginDto.getPassword());
    }



    /**
     * Генерация Basic Access токена для аутентификации.
     */
    public String generateBasicToken(String username, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }
}