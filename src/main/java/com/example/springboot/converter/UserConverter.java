package com.example.springboot.converter;

import com.example.springboot.dto.UserDTO;
import com.example.springboot.model.RoleEntity;
import com.example.springboot.model.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class UserConverter {
    public UserDTO toDTO(UserEntity user) {
        List<Long> roles = new ArrayList<>();
        for (RoleEntity role : user.getRoles()) roles.add(role.getId());
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getCreatedAt(),
                user.getEmail(),
                user.getPhoneNumber(),
                "",
                roles
        );
    }

    public UserEntity toEntity(UserDTO user, Set<RoleEntity> roles) {
        // this method encrypt the user password, have to be the same as the one in
        // WebSecurityConfig
        return new UserEntity(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                new BCryptPasswordEncoder().encode(user.getPassword()).toString(),
                roles
        );
    }
}
