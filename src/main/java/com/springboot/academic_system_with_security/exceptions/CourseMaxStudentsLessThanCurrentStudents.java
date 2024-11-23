package com.springboot.academic_system_with_security.exceptions;

public class CourseMaxStudentsLessThanCurrentStudents extends RuntimeException{

    public CourseMaxStudentsLessThanCurrentStudents(){
    }

    public CourseMaxStudentsLessThanCurrentStudents(String message){
        super(message);
    }

}
