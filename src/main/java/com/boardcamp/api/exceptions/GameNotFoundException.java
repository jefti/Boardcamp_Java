package com.boardcamp.api.exceptions;

public class GameNotFoundException extends RuntimeException{

    public GameNotFoundException(String message){
        super(message);
    }

}
