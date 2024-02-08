package com.boardcamp.api.exceptions;

public class RentalNotFoundException extends RuntimeException{
   
    public RentalNotFoundException(String message){
        super(message);
    }

}
