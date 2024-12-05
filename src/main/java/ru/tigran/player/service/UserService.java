package ru.tigran.player.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tigran.player.service.dto.UserDto;
import ru.tigran.player.model.Role;
import ru.tigran.player.model.UserEntity;
import ru.tigran.player.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity registerUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }
        UserEntity user = new UserEntity();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.valueOf(userDto.getRole().toUpperCase()));
        return userRepository.save(user);
    }
}