package com.example.springboot.Product.converters;

import com.example.springboot.Product.dtos.CategoryDTO;
import com.example.springboot.Product.entities.CategoryEntity;
import com.example.springboot.Product.repositories.CategoryEntityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryConverter {

    private final CategoryEntityRepository categoryRepository;

    public CategoryConverter(CategoryEntityRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDTO entityToDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        if(entity.getParent() != null){
            dto.setParentId(entity.getParent().getId());
        }
        return dto;
    }

    public CategoryEntity dtoToEntity(CategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        if(dto.getParentId() != null){
            CategoryEntity parent = categoryRepository
                    .findById(dto.getParentId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The category with id "+dto.getParentId() +" was not found"));
            entity.setParent(parent);
        }
        return entity;
    }

    public List<CategoryDTO> entityToDTOList(List<CategoryEntity> entities){
        return entities.stream().map(this::entityToDTO).collect(Collectors.toList());
    }
}
