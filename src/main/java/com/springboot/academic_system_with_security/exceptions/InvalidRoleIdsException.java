package com.springboot.academic_system_with_security.exceptions;

public class InvalidRoleIdsException extends RuntimeException{

    public InvalidRoleIdsException(){
    }

    public InvalidRoleIdsException(String message){
        super(message);
    }

}
