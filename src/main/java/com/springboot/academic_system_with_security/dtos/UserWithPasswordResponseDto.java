package com.springboot.academic_system_with_security.dtos;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class UserWithPasswordResponseDto {

    private Long id;

    private String username;

    private String password;

    private List<RoleResponseDto> roleResponseDtoList= new ArrayList<>();

}
