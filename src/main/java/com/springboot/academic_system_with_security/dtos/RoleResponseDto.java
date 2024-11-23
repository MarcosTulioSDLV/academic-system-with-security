package com.springboot.academic_system_with_security.dtos;

import com.springboot.academic_system_with_security.enums.RoleName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RoleResponseDto {

    private Long id;

    private RoleName roleName;

}
