package com.springboot.academic_system_with_security.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CourseStudentScoreRequestDto {

    @NotNull @Positive
    private Long courseId;

    @NotNull @Positive
    private Long studentId;

    @NotNull @PositiveOrZero
    private Double score;

}
