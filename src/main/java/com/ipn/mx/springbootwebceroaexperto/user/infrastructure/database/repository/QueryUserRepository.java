package com.ipn.mx.springbootwebceroaexperto.user.infrastructure.database.repository;


import com.ipn.mx.springbootwebceroaexperto.user.infrastructure.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QueryUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
