package com.springboot.academic_system_with_security.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CourseStudentWithCourseResponseDto {

    private CourseResponseDto courseResponseDto;

    private Double score;

}
