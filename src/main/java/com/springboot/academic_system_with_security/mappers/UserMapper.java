package com.springboot.academic_system_with_security.mappers;

import com.springboot.academic_system_with_security.dtos.*;
import com.springboot.academic_system_with_security.models.Role;
import com.springboot.academic_system_with_security.models.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    private final RoleMapper roleMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper, RoleMapper roleMapper) {
        this.modelMapper = modelMapper;
        this.roleMapper = roleMapper;
    }

    public UserResponseDto toUserResponseDto(UserEntity user){
        UserResponseDto userResponseDto= modelMapper.map(user,UserResponseDto.class);
        List<RoleResponseDto> roleResponseDtoList= convertToRoleDtoList(user.getRoles());
        userResponseDto.setRoleResponseDtoList(roleResponseDtoList);
        return userResponseDto;
    }

    public UserWithPasswordResponseDto toUserWithPasswordResponseDto(UserEntity user){
        UserWithPasswordResponseDto userWithPasswordResponseDto= modelMapper.map(user,UserWithPasswordResponseDto.class);
        List<RoleResponseDto> roleResponseDtoList= convertToRoleDtoList(user.getRoles());
        userWithPasswordResponseDto.setRoleResponseDtoList(roleResponseDtoList);
        return userWithPasswordResponseDto;
    }

    private List<RoleResponseDto> convertToRoleDtoList(List<Role> roles) {
        return roles.stream()
                .map(roleMapper::toRoleResponseDto)
                .toList();
    }

    public UserEntity toUser(UserRequestDto userRequestDto){
        return modelMapper.map(userRequestDto,UserEntity.class);
    }

}
