package com.example.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(CreateUserException ex) {
        UserErrorResponse error = new UserErrorResponse();
        error.setMessage("Account creation failed");
        error.setCause(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(GetUserException ex) {
        UserErrorResponse error = new UserErrorResponse();
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(UpdateUserException ex) {
        UserErrorResponse error = new UserErrorResponse();
        error.setMessage("User update failed");
        error.setCause(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(AccessDeniedException ex) {
        UserErrorResponse error = new UserErrorResponse();
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}
