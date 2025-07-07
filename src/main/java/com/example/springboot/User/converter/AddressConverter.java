package com.example.springboot.User.converter;

import com.example.springboot.User.dto.AddressDTO;
import com.example.springboot.User.entities.AddressEntity;
import com.example.springboot.User.entities.UserEntity;
import com.example.springboot.User.repositories.UserEntityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressConverter {
    private final UserEntityRepository userEntityRepository;

    public AddressConverter(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    public AddressEntity toEntity(AddressDTO dto) {
        AddressEntity entity = new AddressEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setCity(dto.getCity());
        entity.setCountry(dto.getCountry());

        // get user from database
        UserEntity user = userEntityRepository
                .findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The user with id "+dto.getUserId() +" was not found"));
        entity.setUser(user);
        return entity;
    }

    public AddressDTO toDto(AddressEntity entity) {
        AddressDTO dto = new AddressDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setName(entity.getName());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setCity(entity.getCity());
        dto.setCountry(entity.getCountry());
        return dto;
    }

    public List<AddressEntity> toEntityList(List<AddressDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<AddressDTO> toDtoList(List<AddressEntity> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
