package com.example.springboot.Order.repositories;

import com.example.springboot.Order.entities.OrderItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemEntityRepository extends JpaRepository<OrderItemEntity, Long> {
    Page<OrderItemEntity> findAll(Pageable pageable);
}