package com.springboot.academic_system_with_security.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class AddCourseStudentRequestDto {

    @NotNull @Positive
    private Long studentId;

    @NotEmpty
    private List<Long> courseIds= new ArrayList<>();

}
