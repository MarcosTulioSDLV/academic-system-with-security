package com.springboot.academic_system_with_security.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateProfessorRequestDto {

    @NotBlank
    private String document;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String department;

    @NotBlank @Email
    private String email;

    @NotNull @Valid
    private UserRequestDto userRequestDto;

}
