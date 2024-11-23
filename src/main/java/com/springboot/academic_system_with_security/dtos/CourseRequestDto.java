package com.springboot.academic_system_with_security.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CourseRequestDto {

    @NotBlank
    private String courseCode;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull @PositiveOrZero
    private Integer credits;

    @NotNull @PositiveOrZero
    private Integer maxStudents;

}
