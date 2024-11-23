package com.springboot.academic_system_with_security.exceptions;


public class CourseStudentExistsException extends RuntimeException{

    public CourseStudentExistsException(){
    }

    public CourseStudentExistsException(String message){
        super(message);
    }

}
