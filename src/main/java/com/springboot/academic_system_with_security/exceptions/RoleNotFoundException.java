package com.springboot.academic_system_with_security.exceptions;

public class RoleNotFoundException extends RuntimeException{

    public RoleNotFoundException(){
    }

    public RoleNotFoundException(String message){
        super(message);
    }

}
