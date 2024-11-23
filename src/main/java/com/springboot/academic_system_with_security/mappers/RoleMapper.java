package com.springboot.academic_system_with_security.mappers;

import com.springboot.academic_system_with_security.dtos.RoleResponseDto;
import com.springboot.academic_system_with_security.models.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RoleResponseDto toRoleResponseDto(Role role){
        return modelMapper.map(role,RoleResponseDto.class);
    }

}
