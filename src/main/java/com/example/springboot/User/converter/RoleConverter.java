package com.example.springboot.User.converter;

import com.example.springboot.User.dto.RoleDTO;
import com.example.springboot.User.entities.RoleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleConverter {

    private final ModelMapper modelMapper;

    public RoleConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RoleDTO toDTO(RoleEntity roleEntity) {
        return modelMapper.map(roleEntity, RoleDTO.class);
    }

    public RoleEntity toEntity(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, RoleEntity.class);
    }

    public List<RoleEntity> toEntityList(List<RoleDTO> DTOs){
        return DTOs.stream().map(this::toEntity).toList();
    }

    public List<RoleDTO> toDtoList(List<RoleEntity> DTOs){
        return DTOs.stream().map(this::toDTO).toList();
    }
}
