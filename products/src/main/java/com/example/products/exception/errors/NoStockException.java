package com.example.products.exception.errors;

public class NoStockException extends RuntimeException{
    public NoStockException(int code){
        super("Not stock of the product with the id: " + Integer.toString(code));
    }

}
