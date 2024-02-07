package com.boardcamp.api.exceptions;

public class GameConflictException extends RuntimeException{
    
    public GameConflictException(String message){
        super(message);
    }
}
