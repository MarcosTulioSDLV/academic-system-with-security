package com.springboot.academic_system_with_security.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CourseStudentWithStudentResponseDto {

    private StudentResponseDto studentResponseDto;

    private Double score;

}
