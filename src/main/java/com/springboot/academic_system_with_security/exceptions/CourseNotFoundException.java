package com.springboot.academic_system_with_security.exceptions;

public class CourseNotFoundException extends RuntimeException{

    public CourseNotFoundException(){
    }

    public CourseNotFoundException(String message){
        super(message);
    }

}
