package com.springboot.academic_system_with_security.exceptions;


public class CourseMaxStudentsFullException extends RuntimeException{

    public CourseMaxStudentsFullException(){
    }

    public CourseMaxStudentsFullException(String message){
        super(message);
    }

}
