package com.springboot.academic_system_with_security.dtos;

import com.springboot.academic_system_with_security.enums.Gender;
import lombok.*;

@Getter @Setter
public class StudentResponseDto {

    private Long id;

    private String document;

    private String firstName;

    private String lastName;

    private Gender gender;

    private Integer age;//Note:This is retrieved from a method

    private String email;

    private UserResponseDto userResponseDto;

}
