package com.example.springboot.Product.repositories;

import com.example.springboot.Product.entities.ProductInventoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInventoryEntityRepository extends JpaRepository<ProductInventoryEntity, Long> {
    Page<ProductInventoryEntity> findAll(Pageable pageable);
}