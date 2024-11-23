package com.springboot.academic_system_with_security.exceptions;

public class ProfessorEmailExistsException extends RuntimeException{

    public ProfessorEmailExistsException(){
    }

    public ProfessorEmailExistsException(String message){
        super(message);
    }

}
