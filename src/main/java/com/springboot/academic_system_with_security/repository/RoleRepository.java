package com.springboot.academic_system_with_security.repository;

import com.springboot.academic_system_with_security.enums.RoleName;
import com.springboot.academic_system_with_security.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByRoleName(RoleName roleName);

    boolean existsByRoleName(RoleName roleName);

}
