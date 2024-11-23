package com.springboot.academic_system_with_security.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CourseResponseDto {

    private Long id;

    private String courseCode;

    private String name;

    private String description;

    private Integer credits;

    private Integer maxStudents;

    private Integer currentStudents;

    private ProfessorResponseDto professorResponseDto;

}
