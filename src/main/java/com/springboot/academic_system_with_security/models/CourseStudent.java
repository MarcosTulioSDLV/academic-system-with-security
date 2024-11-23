package com.springboot.academic_system_with_security.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TB_COURSE_STUDENT")
@AllArgsConstructor
@Getter @Setter @ToString
public class CourseStudent {

    @EmbeddedId
    private CourseStudentId id= new CourseStudentId();

    @ManyToOne
    @JoinColumn(name = "course_id",insertable = false,updatable = false)
    @MapsId(value = "courseId")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id",insertable = false,updatable = false)
    @MapsId(value = "studentId")
    private Student student;

    @Column(nullable = false)
    private Double score;

    public CourseStudent(){
    }

    public CourseStudent(Course course,Student student,Double score){
        this.course= course;
        this.student= student;
        this.score= score;
    }

}
