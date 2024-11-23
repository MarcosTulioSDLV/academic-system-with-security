package com.springboot.academic_system_with_security.exceptions;

public class StudentEmailExistsException extends RuntimeException{

    public StudentEmailExistsException(){
    }

    public StudentEmailExistsException(String message){
        super(message);
    }

}
