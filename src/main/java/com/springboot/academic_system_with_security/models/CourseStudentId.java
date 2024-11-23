package com.springboot.academic_system_with_security.models;

import jakarta.persistence.Embeddable;
import lombok.*;

@AllArgsConstructor
@Getter @Setter @ToString
@EqualsAndHashCode
@Embeddable
public class CourseStudentId {

    private static final long serialVersionUID=1;

    private Long courseId;

    private Long studentId;

    public CourseStudentId(){
    }

}
