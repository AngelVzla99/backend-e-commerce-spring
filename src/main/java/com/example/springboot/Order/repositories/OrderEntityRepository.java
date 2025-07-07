package com.example.springboot.Order.repositories;

import com.example.springboot.Order.entities.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {
    Page<OrderEntity> findAll(Pageable pageable);
}