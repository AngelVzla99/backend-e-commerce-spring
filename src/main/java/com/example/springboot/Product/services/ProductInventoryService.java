package com.example.springboot.Product.services;

import com.example.springboot.Product.converters.ProductInventoryConverter;
import com.example.springboot.Product.dtos.ProductInventoryDTO;
import com.example.springboot.Product.entities.ProductEntity;
import com.example.springboot.Product.entities.ProductInventoryEntity;
import com.example.springboot.Product.repositories.ProductInventoryEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ProductInventoryService {
    @Autowired
    private ProductInventoryEntityRepository productInventoryRepository;
    @Autowired
    private ProductInventoryConverter productInventoryConverter;

    public Page<ProductInventoryDTO> findAllPageable(Pageable pageable) {
        Page<ProductInventoryEntity> responsePage = productInventoryRepository.findAll(pageable);
        if (!responsePage.hasContent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // convert the resulting list to dto
        return responsePage.map(productInventoryConverter::convertEntityToDto);
    }

    public ProductInventoryDTO findById(Long id) {
        Optional<ProductInventoryEntity> product = productInventoryRepository.findById(id);
        if (product.isPresent()) return productInventoryConverter.convertEntityToDto(product.get());
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public ProductInventoryDTO save(ProductInventoryDTO productEntity) {
        ProductInventoryEntity entity = productInventoryConverter.convertDtoToEntity(productEntity);
        // update the amount
        ProductEntity product = entity.getProduct();
        product.setQuantity( product.getQuantity() + entity.getQuantity() );
        // save in the database
        productInventoryRepository.save(entity);
        return productInventoryConverter.convertEntityToDto(entity);
    }

    public void delete(Long id) {
        if( productInventoryRepository.findById(id).isEmpty() )
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"This product inventory doesn't exist");
        productInventoryRepository.deleteById(id);
    }
}
