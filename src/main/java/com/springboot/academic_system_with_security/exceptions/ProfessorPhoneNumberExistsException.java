package com.springboot.academic_system_with_security.exceptions;

public class ProfessorPhoneNumberExistsException extends RuntimeException{

    public ProfessorPhoneNumberExistsException(){
    }

    public ProfessorPhoneNumberExistsException(String message){
        super(message);
    }

}
