package com.learning.hotelmanagementapplication.Exceptions;

import com.learning.hotelmanagementapplication.DTOs.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleNotFoundException(NotFoundException notFoundException) {
        return new ResponseEntity<>(new ExceptionDTO(notFoundException.getMessage(),
                HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialException.class)
    public ResponseEntity<ExceptionDTO> handleBadCredentialException(BadCredentialException badCredentialException) {
        return new ResponseEntity<>(new ExceptionDTO(badCredentialException.getMessage(),
                HttpStatus.NOT_FOUND), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AlreadyPresentException.class)
    public ResponseEntity<ExceptionDTO> handleAlreadyPresentException(AlreadyPresentException alreadyPresentException) {
        return new ResponseEntity<>(new ExceptionDTO(alreadyPresentException.getMessage(),
                HttpStatus.NOT_FOUND), HttpStatus.CONFLICT);
    }

}