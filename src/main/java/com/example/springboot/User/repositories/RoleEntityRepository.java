package com.example.springboot.User.repositories;

import com.example.springboot.User.entities.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findOneByRoleName(String roleName );

    Page<RoleEntity> findAll(Pageable pageable);
}