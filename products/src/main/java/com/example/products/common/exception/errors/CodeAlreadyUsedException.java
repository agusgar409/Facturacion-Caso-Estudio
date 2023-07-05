package com.example.products.common.exception.errors;

public class CodeAlreadyUsedException extends RuntimeException{

    public CodeAlreadyUsedException(int code){

        super("code: " + Integer.toString(code ) + " already exists");
    }
}
