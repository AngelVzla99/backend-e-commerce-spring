package com.example.springboot.Product.repositories;

import com.example.springboot.Product.entities.ProductDiscountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDiscountEntityRepository extends JpaRepository<ProductDiscountEntity, Long> {
    Page<ProductDiscountEntity> findAll(Pageable pageable);
}