package com.example.products.common.exception.handler;


import com.example.products.common.exception.errors.CodeAlreadyUsedException;
import com.example.products.common.exception.errors.NoStockException;
import com.example.products.common.exception.errors.ProductNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
/**
 * @author jrodriguez
 */
@RestControllerAdvice
public class GlobalHandlerException{


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            CodeAlreadyUsedException.class,
            NoStockException.class,
            javax.validation.ConstraintViolationException.class,
            javax.validation.UnexpectedTypeException.class,
            org.springframework.web.bind.MethodArgumentNotValidException.class
    })
    @ResponseBody
    protected ExceptionDetails badRequestHandler(Exception exception, HttpServletRequest request) {
        return new ExceptionDetails(LocalDateTime.now(), exception, request);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            ProductNotFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            ChangeSetPersister.NotFoundException.class
    })
    @ResponseBody
    protected ExceptionDetails notFoundHandler(Exception exception, HttpServletRequest request) {
        return new ExceptionDetails(LocalDateTime.now(), exception, request);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    protected ExceptionDetails exceptionHandler(Exception exception, HttpServletRequest request) {
        return new ExceptionDetails(LocalDateTime.now(), exception, request);
    }

}