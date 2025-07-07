package com.example.springboot.Product.converters;

import com.example.springboot.Product.dtos.ProductDiscountDTO;
import com.example.springboot.Product.entities.ProductDiscountEntity;
import com.example.springboot.Product.repositories.ProductEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductDiscountConverter {
    private final ModelMapper modelMapper;
    private final ProductEntityRepository productRepository;

    public ProductDiscountConverter(ModelMapper modelMapper, ProductEntityRepository productRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    public ProductDiscountDTO toDTO(ProductDiscountEntity productDiscount) {
        ProductDiscountDTO dto = modelMapper.map(productDiscount, ProductDiscountDTO.class);
        return dto;
    }

    public ProductDiscountEntity toEntity(ProductDiscountDTO productDiscount) {
        ProductDiscountEntity entity = modelMapper.map(productDiscount, ProductDiscountEntity.class);
        return entity;
    }
}
