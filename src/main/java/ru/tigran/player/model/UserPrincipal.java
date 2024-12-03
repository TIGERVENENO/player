package ru.tigran.player.model;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
public class UserPrincipal extends User {
    private final UserEntity userEntity;

    public UserPrincipal(UserEntity userEntity) {
        super(userEntity.getUsername(), userEntity.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole().name())));
        this.userEntity = userEntity;
    }

}