package com.springboot.academic_system_with_security.exceptions;

public class RoleNameNotFoundException extends RuntimeException{

    public RoleNameNotFoundException(){
    }

    public RoleNameNotFoundException(String message){
        super(message);
    }

}
