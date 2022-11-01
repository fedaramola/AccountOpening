package com.ecobank.FcubsAccountCreation.exception.controller;


import com.ecobank.FcubsAccountCreation.dto.ErrorResponse;
import com.ecobank.FcubsAccountCreation.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


import java.util.stream.Collectors;

import static com.ecobank.FcubsAccountCreation.constant.ResponseCodes.*;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles method validation exceptions
     *
     * @param ex: the exception
     * @return the response entity
     */

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<ErrorResponse> handlerMethodValidation(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("|"));
        return ResponseEntity.badRequest().body(new ErrorResponse(BAD_DATA, errors));
    }

    /**
     * Handles constraint validation exceptions
     *
     * @param ex: the exception
     * @return the response entity
     */

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<ErrorResponse> handleConstraintValidations(ConstraintViolationException ex) {

        String errors = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(" | "));

        return ResponseEntity.badRequest().body(new ErrorResponse(BAD_DATA, errors));
    }

    @ExceptionHandler(value = {AlreadyExistException.class})
    protected ResponseEntity<ErrorResponse> handleAlreadyExistsException(AlreadyExistException ex, HttpServletRequest request) {
        log.error("Resource not found exception occurred while trying to process request: {}", request.getRequestURI());
        return ResponseEntity.badRequest().body(new ErrorResponse(ALREADY_EXIST, ex.getMessage()));
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(HttpServletRequest request, ResourceNotFoundException ex) {
        log.error("Resource not found exception occurred while trying to process request: {}", request.getRequestURI());
        ResponseEntity<ErrorResponse> response = new ResponseEntity<>(new ErrorResponse(NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
        return response;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception ex,
                                                HttpServletRequest request, HttpServletResponse response) {
        log.error("Exception occurred while processing the request: {} because: {}", request.getRequestURI(), ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body((new ErrorResponse(PROCESS_ERROR, "An unknown exception just occurred. Please try again or contact administrator")));
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(CustomAuthenticationException ex) {
        log.error("Exception occurred while trying to authenticate user because: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(VALIDATION_ERROR, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(HttpServletRequest request, ValidationException ex) {
        log.error("validation exception occurred while trying to process request: {} because: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(VALIDATION_ERROR, ex.getMessage()));

    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoDataFoundException(HttpServletRequest request, NoDataFoundException ex) {
        log.error("validation exception occurred while trying to process request: {} because: {}", request.getRequestURI(), ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(NOT_FOUND, ex.getMessage()), HttpStatus.NO_CONTENT);
    }


    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> handleGenericException(HttpServletRequest request, GenericException ex) {
        log.error("generic exception occurred while trying to process request: {} because: {}", request.getRequestURI(), ex.getMessage());

        return ResponseEntity.status((ex.getHttpStatus() == null) ? HttpStatus.INTERNAL_SERVER_ERROR : ex.getHttpStatus()).body(new ErrorResponse(ex.getCode(), (ex.getMessage() == null) ? ex.getCode().getMessage() : ex.getMessage()));
    }


}
