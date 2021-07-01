package com.hapoalim.employee.employee.exception;

// here we have a constant message, depanding only on the id variable, so no need for a message class member.

public class EmployeeNotFoundException extends RuntimeException {
    private String message;

    public EmployeeNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public EmployeeNotFoundException() {
    }
}
