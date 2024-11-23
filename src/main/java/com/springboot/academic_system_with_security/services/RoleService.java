package com.springboot.academic_system_with_security.services;

import com.springboot.academic_system_with_security.dtos.RoleRequestDto;
import com.springboot.academic_system_with_security.dtos.RoleResponseDto;
import com.springboot.academic_system_with_security.enums.RoleName;
import com.springboot.academic_system_with_security.models.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    Page<RoleResponseDto> getAllRoles(Pageable pageable);

    RoleResponseDto getRoleById(Long id);

    Role findRoleByRoleName(RoleName roleName);

    List<Role> findRolesByIds(List<Long> ids);

    /*@Transactional
    RoleResponseDto addRole(RoleRequestDto roleRequestDto);*/

}