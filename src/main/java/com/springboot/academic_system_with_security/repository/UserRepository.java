package com.springboot.academic_system_with_security.repository;


import com.springboot.academic_system_with_security.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);

}