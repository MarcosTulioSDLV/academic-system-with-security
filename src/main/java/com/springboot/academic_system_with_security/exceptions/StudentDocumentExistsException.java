package com.springboot.academic_system_with_security.exceptions;

public class StudentDocumentExistsException extends RuntimeException{

    public StudentDocumentExistsException(){
    }

    public StudentDocumentExistsException(String message){
        super(message);
    }

}
