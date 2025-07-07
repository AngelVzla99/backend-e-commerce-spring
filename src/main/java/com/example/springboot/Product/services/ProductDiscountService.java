package com.example.springboot.Product.services;

import com.example.springboot.Product.entities.ProductEntity;
import com.example.springboot.Product.repositories.ProductEntityRepository;
import com.example.springboot.Product.converters.ProductDiscountConverter;
import com.example.springboot.Product.dtos.ProductDiscountDTO;
import com.example.springboot.Product.entities.ProductDiscountEntity;
import com.example.springboot.Product.repositories.ProductDiscountEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductDiscountService {
    @Autowired
    ProductDiscountEntityRepository productDiscountEntityRepository;
    @Autowired
    ProductDiscountConverter productDiscountConverter;
    @Autowired
    ProductEntityRepository productEntityRepository;

    public Page<ProductDiscountDTO> findAllPageable(Pageable pageable) {
        Page<ProductDiscountEntity> responsePage = productDiscountEntityRepository.findAll(pageable);
        if (!responsePage.hasContent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // convert the resulting list to dto
        return responsePage.map(productDiscountConverter::toDTO);
    }

    public ProductDiscountDTO findById(Long id) {
        Optional<ProductDiscountEntity> productDiscount = productDiscountEntityRepository.findById(id);
        if (productDiscount.isPresent()) return productDiscountConverter.toDTO(productDiscount.get());
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ProductDiscountDTO save(ProductDiscountDTO discount) {
        ProductDiscountEntity productDiscountE = productDiscountConverter.toEntity(discount);
        ProductDiscountEntity productDiscount = productDiscountEntityRepository.save(productDiscountE);
        // === set productDiscounts ===
        List<ProductEntity> products = productEntityRepository.findByIdIn(discount.getProductIds());
        // if some products are not found => 404
        List<Long> diff = new LinkedList<>(discount.getProductIds()); // linkedList => hash table for removeAll
        diff.removeAll( products.stream().map(ProductEntity::getId).toList() );
        if(diff.size()>0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The products with ids "+ diff  +" was not found");
        products.forEach(product -> product.setProductDiscount(productDiscount));
        // === save in the database ===
        return productDiscountConverter.toDTO(productDiscount);
    }

    public void delete(Long id) {
        if( productDiscountEntityRepository.findById(id).isEmpty() )
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"This discount doesn't exist");
        productDiscountEntityRepository.deleteById(id);
    }
}
