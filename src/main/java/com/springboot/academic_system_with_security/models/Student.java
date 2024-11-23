package com.springboot.academic_system_with_security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.academic_system_with_security.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_STUDENT")
@AllArgsConstructor
@Getter @Setter @ToString(exclude = "courseStudentList")
//@EqualsAndHashCode(exclude = "courseStudentList")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String document;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false,unique = true)
    private String email;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @JsonIgnore
    @OneToMany(mappedBy = "student",cascade = CascadeType.REMOVE)
    private List<CourseStudent> courseStudentList= new ArrayList<>();

    public Student(){
    }

    public Integer getAge(){
        return LocalDate.now().getYear()-birthDate.getYear();
    }

}

