package com.lostlink.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException exception) {


        String message = exception
                .getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();


        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        400,
                        message
                );


        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex) {


        ErrorResponse errorResponse =
                new ErrorResponse(
                        LocalDateTime.now(),
                        404,
                        ex.getMessage()
                );


        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.NOT_FOUND
        );
    }
}