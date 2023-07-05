package com.example.Items.common.exeption;


import errors.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.BadRequestException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ExeptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<MessagesInfo> handleValidationError(HttpServletRequest request, ConstraintViolationException exception) {
        String message = exception.getMessage();
        MessagesInfo errorInfo = new MessagesInfo(message, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<MessagesInfo> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        String message = e.getMessage();
        MessagesInfo errorInfo = new MessagesInfo(message, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<MessagesInfo> methodArgumentTypeExcept(HttpServletRequest request, MethodArgumentTypeMismatchException exception) {
        String message = exception.getMessage();
        MessagesInfo errorInfo = new MessagesInfo(message, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<MessagesInfo> badException(HttpServletRequest request, BadRequestException exception) {
        String message = exception.getMessage();
        MessagesInfo errorInfo = new MessagesInfo(message, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<MessagesInfo> illegalArgExcept(HttpServletRequest request, IllegalArgumentException ex) {
        String message = ex.getMessage();
        MessagesInfo errorInfo = new MessagesInfo(message, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<MessagesInfo> nullPointerException(HttpServletRequest request, NullPointerException exception) {
        String message = exception.getMessage();
        MessagesInfo errorInfo = new MessagesInfo(message, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    //exepciones de proyecto

    @ExceptionHandler({CategoryInvalidException.class})
    public ResponseEntity<MessagesInfo> categoryInvalid(HttpServletRequest request, CategoryInvalidException exception) {
        String message = exception.getMessage();
        MessagesInfo errorInfo = new MessagesInfo(message, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({StatusInvalidException.class})
    public ResponseEntity<MessagesInfo> statusInvalid(HttpServletRequest request, StatusInvalidException exeption) {

        String message = exeption.getMessage();
        MessagesInfo errorInfo = new MessagesInfo(message, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class,
            ProductNotFoundException.class})
    public ResponseEntity<MessagesInfo> notFoundExcept(HttpServletRequest request, NotFoundException exception) {
        String message = exception.getMessage();
        MessagesInfo errorInfo = new MessagesInfo(message, HttpStatus.NOT_FOUND.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DeletionInvalidException.class})
    public ResponseEntity<MessagesInfo> deletionInvalidExcept(HttpServletRequest request, DeletionInvalidException exception) {
        String message = exception.getMessage();
        MessagesInfo errorInfo = new MessagesInfo(message, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CreationClassException.class})
    public ResponseEntity<MessagesInfo> deletionInvalidExcept(HttpServletRequest request, CreationClassException exception) {
        String message = exception.getMessage();
        MessagesInfo errorInfo = new MessagesInfo(message, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    protected MessagesInfo exceptionHandler(Exception exception, HttpServletRequest request) {
        return new MessagesInfo(LocalDateTime.now(), exception, request);
    }

}
