package com.example.springboot.service;

import com.example.springboot.converter.CategoryConverter;
import com.example.springboot.dto.CategoryDTO;
import com.example.springboot.dto.ProductDTO;
import com.example.springboot.model.CategoryEntity;
import com.example.springboot.model.ProductEntity;
import com.example.springboot.repository.CategoryEntityRepository;
import com.example.springboot.repository.ProductEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryEntityRepository categoryRepository;
    @Autowired
    private CategoryConverter categoryConverter;

    public List<CategoryDTO> findFirstCategories(int x) {
        Pageable pageable = PageRequest.of(0, x);
        List<CategoryEntity> categories = categoryRepository.findAll(pageable).getContent();
        return categoryConverter.entityToDTOList(categories);
    }

    public Page<CategoryDTO> findAllPageable(Pageable pageable) {
        Page<CategoryEntity> entitiesPage = categoryRepository.findAll(pageable);
        if( !entitiesPage.hasContent() )
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // convert the resulting list to dto
        Page<CategoryDTO> DTOs = entitiesPage.map(categoryConverter::entityToDTO);
        return DTOs;
    }

    public CategoryDTO save(CategoryDTO categoryDto) {
        CategoryEntity categoryEntity = categoryConverter.dtoToEntity(categoryDto);
        return categoryConverter.entityToDTO( categoryRepository.save(categoryEntity) );
    }

    public void delete(Long id) {
        // find in the database
        if( categoryRepository.findById(id).isEmpty() )
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"There is no category with id "+id);
        // delete from the db
        categoryRepository.deleteById(id);
    }
}
