package com.boardcamp.api.exceptions;

public class RentalUnprocessableEntityException extends RuntimeException{
    
    public RentalUnprocessableEntityException(String message){
        super(message);
    }
}
