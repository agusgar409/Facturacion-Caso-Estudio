package com.example.products.exception.errors;

public class CodeAlreadyUsedException extends RuntimeException{

    public CodeAlreadyUsedException(int code){

        super("code: " + Integer.toString(code ) + " already exists");
    }
}
