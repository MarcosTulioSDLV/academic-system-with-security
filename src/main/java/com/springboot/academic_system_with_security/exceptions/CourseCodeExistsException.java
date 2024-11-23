package com.springboot.academic_system_with_security.exceptions;

public class CourseCodeExistsException extends RuntimeException{

    public CourseCodeExistsException(){
    }

    public CourseCodeExistsException(String message){
        super(message);
    }

}
