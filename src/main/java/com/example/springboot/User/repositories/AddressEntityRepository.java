package com.example.springboot.User.repositories;

import com.example.springboot.User.entities.UserEntity;
import com.example.springboot.User.entities.AddressEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressEntityRepository extends JpaRepository<AddressEntity, Long> {
    Page<AddressEntity> findAll(Pageable pageable);

    Optional<AddressEntity> findByUserAndId(UserEntity user, Long id);
}