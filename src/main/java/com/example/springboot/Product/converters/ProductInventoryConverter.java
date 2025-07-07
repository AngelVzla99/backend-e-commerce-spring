package com.example.springboot.Product.converters;

import com.example.springboot.Product.dtos.ProductInventoryDTO;
import com.example.springboot.Product.entities.ProductInventoryEntity;
import com.example.springboot.Product.entities.ProductEntity;
import com.example.springboot.Product.repositories.ProductEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ProductInventoryConverter {

    private final ModelMapper modelMapper;
    private final ProductEntityRepository productEntityRepository;

    public ProductInventoryConverter(ModelMapper modelMapper, ProductEntityRepository productEntityRepository) {
        this.modelMapper = modelMapper;
        this.productEntityRepository = productEntityRepository;
    }

    public ProductInventoryDTO convertEntityToDto(ProductInventoryEntity entity) {
        ProductInventoryDTO dto = modelMapper.map(entity, ProductInventoryDTO.class);
        dto.setProductId(entity.getProduct().getId());
        return dto;
    }

    public ProductInventoryEntity convertDtoToEntity(ProductInventoryDTO dto) {
        ProductInventoryEntity entity = modelMapper.map(dto, ProductInventoryEntity.class);
        ProductEntity productEntity = productEntityRepository
                .findById(dto.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The product with id "+dto.getProductId() +" was not found"));
        entity.setProduct(productEntity);
        return entity;
    }
}
