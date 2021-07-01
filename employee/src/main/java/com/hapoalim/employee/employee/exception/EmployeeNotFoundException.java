package com.hapoalim.employee.employee.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(long id) {
        super("could not find employee " + id);
    }
}
