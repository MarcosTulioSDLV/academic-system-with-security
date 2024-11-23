package com.springboot.academic_system_with_security.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProfessorWithPasswordResponseDto {

    private Long id;

    private String document;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String department;

    private String email;

    private UserWithPasswordResponseDto userWithPasswordResponseDto;

}
