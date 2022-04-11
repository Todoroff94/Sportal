package com.example.sportalproject.exceptions;

public class UnauthorisedException extends RuntimeException{

    public UnauthorisedException(String msg){
        super(msg);
    }
}
