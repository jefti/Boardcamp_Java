package com.boardcamp.api.exceptions;

public class CustomerConflictException extends RuntimeException{

    public CustomerConflictException(String message){
        super(message);
    }

}
