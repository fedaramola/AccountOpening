package com.ecobank.FcubsAccountCreation.exception;

public class CustomAuthenticationException extends RuntimeException{
    public CustomAuthenticationException(String message){
        super(message);
    }
}
