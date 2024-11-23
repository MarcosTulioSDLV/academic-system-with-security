package com.springboot.academic_system_with_security.exceptions;

public class ProfessorDocumentExistsException extends RuntimeException{

    public ProfessorDocumentExistsException(){
    }

    public ProfessorDocumentExistsException(String message){
        super(message);
    }


}
