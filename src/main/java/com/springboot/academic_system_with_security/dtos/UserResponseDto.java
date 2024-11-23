package com.springboot.academic_system_with_security.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class UserResponseDto {

    private Long id;

    private String username;

    private List<RoleResponseDto> roleResponseDtoList= new ArrayList<>();

}
