package com.springboot.academic_system_with_security.exceptions;

public class ProfessorNotFoundException extends RuntimeException{

    public ProfessorNotFoundException(){
    }

    public ProfessorNotFoundException(String message){
        super(message);
    }

}
