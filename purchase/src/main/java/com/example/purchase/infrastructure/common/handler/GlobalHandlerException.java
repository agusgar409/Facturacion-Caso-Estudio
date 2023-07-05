package com.example.purchase.infrastructure.common.handler;


import errors.DeletionInvalidException;
import errors.NotFoundException;
import errors.StatusInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import java.time.LocalDateTime;

/**
 * @author jrodriguez
 */
@RestControllerAdvice
public class GlobalHandlerException {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            StatusInvalidException.class,
            IllegalArgumentException.class,
            org.springframework.web.bind.MethodArgumentNotValidException.class,
            DeletionInvalidException.class,
            javax.validation.ConstraintViolationException.class,
            javax.validation.UnexpectedTypeException.class,
            org.springframework.web.bind.MethodArgumentNotValidException.class,
            BadRequestException.class
    })
    @ResponseBody
    protected ExceptionDetails badRequestHandler(Exception exception, HttpServletRequest request) {
        return new ExceptionDetails(LocalDateTime.now(), exception, request);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            NotFoundException.class,
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
