package com.springboot.academic_system_with_security.exceptions;


public class RoleNameExistsException extends RuntimeException{

    public RoleNameExistsException(){
    }

    public RoleNameExistsException(String message){
        super(message);
    }

}
