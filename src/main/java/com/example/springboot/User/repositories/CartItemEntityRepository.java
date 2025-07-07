package com.example.springboot.User.repositories;

import com.example.springboot.User.entities.AddressEntity;
import com.example.springboot.User.entities.CartItemEntity;
import com.example.springboot.User.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemEntityRepository extends JpaRepository<CartItemEntity, Long> {
    Page<CartItemEntity> findAll(Pageable pageable);

    Optional<CartItemEntity> deleteByUserAndId(UserEntity user, Long id);
}