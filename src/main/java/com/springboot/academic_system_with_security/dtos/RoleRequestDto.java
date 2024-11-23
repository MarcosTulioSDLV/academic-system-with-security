package com.springboot.academic_system_with_security.dtos;

import com.springboot.academic_system_with_security.enums.RoleName;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RoleRequestDto {

    @NotNull
    RoleName roleName;

}
