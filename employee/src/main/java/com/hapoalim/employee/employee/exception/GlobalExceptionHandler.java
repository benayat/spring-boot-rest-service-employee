package com.hapoalim.employee.employee.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value(value = "${data.exception.message1}")
    private String message1;
    @Value(value = "${data.exception.message2}")
    private String message2;
    @Value(value = "${data.exception.message3}")
    private String message3;
    @Value(value = "${data.exception.message4}")
    private String message4;

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> EmployeeNotFoundException(EmployeeNotFoundException employeeNotFoundException) {
        return new ResponseEntity<String>(message1, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<String>(message2, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = EmployeeAlreadyExistsException.class)
    public ResponseEntity<String> EmployeeAlreadyExistsException(
            EmployeeAlreadyExistsException employeeAlreadyExistsException) {
        return new ResponseEntity<String>(message3, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> GeneralException(Exception exception) {
        return new ResponseEntity<String>(message4, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
