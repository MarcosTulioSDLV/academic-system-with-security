package com.springboot.academic_system_with_security.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
public class UserRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
