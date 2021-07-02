package com.hapoalim.employee.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String message1 = "EMPLOYEE NOT FOUND";
    private static final String message2 = "INVALID ARGUMENTS";
    private static final String message3 = "EMPLOYEE ALREADY EXISTS";
    private static final String message4 = "WE HAVE AN INTERNAL SERVER ERROR";

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> EmployeeNotFoundException(EmployeeNotFoundException ex) {
        return new ResponseEntity<String>(message1, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<String>(message2, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = EmployeeAlreadyExistsException.class)
    public ResponseEntity<String> EmployeeAlreadyExistsException(EmployeeAlreadyExistsException ex) {

        return new ResponseEntity<String>(message3, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> GeneralException(Exception exception) {
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        // return new ResponseEntity<String>(message4,
        // HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
