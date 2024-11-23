package com.springboot.academic_system_with_security.exceptions;

public class CourseStudentNotFoundException extends RuntimeException{

    public CourseStudentNotFoundException(){
    }

    public CourseStudentNotFoundException(String message){
        super(message);
    }

}
