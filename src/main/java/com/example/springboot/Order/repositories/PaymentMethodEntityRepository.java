package com.example.springboot.Order.repositories;

import com.example.springboot.Order.entities.PaymentMethodEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodEntityRepository extends JpaRepository<PaymentMethodEntity, Long> {
    Page<PaymentMethodEntity> findAll(Pageable pageable);
}