package com.boardcamp.api.exceptions;

public class CustomerNotFoundException extends RuntimeException{
    
    public CustomerNotFoundException(String message){
        super(message);
    }
}
