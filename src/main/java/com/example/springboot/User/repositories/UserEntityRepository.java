package com.example.springboot.User.repositories;

import com.example.springboot.Product.entities.ProductEntity;
import com.example.springboot.User.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    public List<UserEntity> findByEmail(String email);

    Page<UserEntity> findAll(Pageable pageable);

    Optional<UserEntity> findOneByEmail( String email );
}