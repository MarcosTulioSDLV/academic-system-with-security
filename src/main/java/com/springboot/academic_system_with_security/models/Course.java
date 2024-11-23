package com.springboot.academic_system_with_security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.academic_system_with_security.exceptions.CourseMaxStudentsFullException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_COURSE")
@AllArgsConstructor
@Getter @Setter @ToString(exclude = "courseStudentList")
//@EqualsAndHashCode(exclude = "courseStudentList")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String courseCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer credits;

    @Column(nullable = false)
    private Integer maxStudents;

    @Column(nullable = false)
    private Integer currentStudents;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @JsonIgnore
    @OneToMany(mappedBy = "course",cascade = CascadeType.REMOVE)
    private List<CourseStudent> courseStudentList= new ArrayList<>();

    public Course(){
    }

    private boolean canEnrollMoreStudents(){
        return currentStudents<maxStudents;
    }

    public void enrollStudent() {
        if(!canEnrollMoreStudents()){
            String message = String.format("The Max Students for the Course: %s, Course Code: %s, is full!", name, courseCode);
            throw new CourseMaxStudentsFullException(message);
        }
        currentStudents++;
    }

    public void unenrollStudent(){
        currentStudents--;
    }

}
