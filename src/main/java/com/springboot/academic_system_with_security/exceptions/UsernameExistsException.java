package com.springboot.academic_system_with_security.exceptions;

public class UsernameExistsException extends RuntimeException{

    public UsernameExistsException(){
    }

    public UsernameExistsException(String message){
        super(message);
    }

}
