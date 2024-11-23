package com.springboot.academic_system_with_security.services;

import com.springboot.academic_system_with_security.dtos.RoleRequestDto;
import com.springboot.academic_system_with_security.dtos.RoleResponseDto;
import com.springboot.academic_system_with_security.enums.RoleName;
import com.springboot.academic_system_with_security.exceptions.RoleNameExistsException;
import com.springboot.academic_system_with_security.exceptions.RoleNameNotFoundException;
import com.springboot.academic_system_with_security.exceptions.RoleNotFoundException;
import com.springboot.academic_system_with_security.mappers.RoleMapper;
import com.springboot.academic_system_with_security.models.Role;
import com.springboot.academic_system_with_security.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService{

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public Page<RoleResponseDto> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable).map(roleMapper::toRoleResponseDto);
    }

    @Override
    public RoleResponseDto getRoleById(Long id) {
        Role role= findRoleById(id);
        return roleMapper.toRoleResponseDto(role);
    }

    private Role findRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("Role with id: " + id + " not found!"));
    }

    public Role findRoleByRoleName(RoleName roleName){
        return roleRepository.findByRoleName(roleName).orElseThrow(()-> new RoleNameNotFoundException("Role Name: "+roleName+" not found!"));
    }

    public List<Role> findRolesByIds(List<Long> ids){
        //METHOD 1 (This even accepts not existing role ids, if some or all ids are not found, no entities are returned for these IDs.)
        //This could return an empty list, or the size can be equal or less than the number of given ids. UserServiceImp-validateNotEmptyList() method would be necessary!.
        return roleRepository.findAllById(ids);

        //METHOD 2 (By using previous findRoleById method. Because inner exceptions it will never return an empty list so UserServiceImp-validateNotEmptyList() method would not be necessary).
        //return ids.stream().map(this::findRoleById).collect(toList());
    }


    /*@Override
    @Transactional
    public RoleResponseDto addRole(RoleRequestDto roleRequestDto) {
        Role role= new Role(null,roleRequestDto.getRoleName(),null);
        validateUniqueFields(role);
        return roleMapper.toRoleResponseDto(roleRepository.save(role));
    }

    private void validateUniqueFields(Role role) {
        if(roleRepository.existsByRoleName(role.getRoleName())){
            throw new RoleNameExistsException("Role with RoleName: "+ role.getRoleName()+" already exists!");
        }
    }*/

}