package com.springboot.academic_system_with_security.exceptions;

public class StudentNotFoundException extends RuntimeException{

    public StudentNotFoundException(){
    }

    public StudentNotFoundException(String message){
        super(message);
    }

}
