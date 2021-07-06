package com.hapoalim.employee.employee.controller;

import java.util.List;

import javax.validation.Valid;

import com.hapoalim.employee.employee.model.Employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

// rest http requests imports
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// service imporst
import com.hapoalim.employee.employee.service.EmployeeService;
/* 
telling java to treat this as a controller - which has mappings, and parsed as responseBody.
the controller has a repository attribute, where we keep all the employees.
the mappings and methods are simple, nothing to add.
* I'll handle the validation of the parameters in the employee service itself and devide concerns.
*/

@RestController
public class EmployeeController {
    private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    // @Inject
    EmployeeService employeeService;

    @Autowired
    EmployeeController(EmployeeService service) {
        this.employeeService = service;
    }

    /*
     * method: getAll: returns a List of all the employees currently in the
     * repository.
     * 
     * @validation: throws IllegalArgumentException if there's a problem with the
     * input.
     * 
     */
    @GetMapping(value = "/employees")
    Iterable<Employee> getAllEmployees() {
        return this.employeeService.getAll();
    }
    /*
     * method: getByName: returns all employees by the given name.
     * 
     * @params: name - a query parameter.
     * 
     * @validation: if it's null - it will throw IllegalArgumentException.
     * 
     * @return: the employees are found, or the custom error
     * EmployeeNotFoundException.
     */

    @GetMapping(value = "/employees/search/byname")
    List<Employee> getByName(@Valid @RequestParam String name) {
        logger.info("get employee by the name of " + name);
        return this.employeeService.getByName(name);
    }

    /*
     * method: getOne: returns an employee bi id.
     * 
     * @params: id - a path parameter.
     * 
     * @validation: if it's null - it will throw IllegalArgumentException.
     * 
     * @return: the employee if found, or the custom error
     * EmployeeNotFoundException.
     */
    @GetMapping("/employees/{id}")
    Employee getOne(@Valid @PathVariable String id) {
        logger.info("get the employee with an id of " + id);
        return this.employeeService.getOne(id);
    }

    /*
     * method: addNewEmployee: self explanatory.
     * 
     * @params: the new employee.
     * 
     * @validation: checks the employee with the validators from the employee class.
     * 
     */
    @PostMapping("/employees")
    Employee addNewEmployee(@Valid @RequestBody Employee newEmployee) {
        logger.info("adding a new employee: " + newEmployee);
        return this.employeeService.addNewEmployee(newEmployee);
    }

    // Single item
    // throws IllegalArgumentException if id is null.

    /*
     * method: replaceEmployee: PUT method to update an employee, or create a new
     * one if he doesn't exist.
     * 
     * @params: id - path parameter.
     * 
     * @return: the new/updated employee.
     */
    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@Valid @RequestBody Employee newEmployee, @PathVariable String id) {
        logger.info("handling request: replacing employee " + newEmployee);
        return this.employeeService.replaceEmployee(newEmployee, id);
    }

    // query params are inside {} in java.
    /*
     * delete an employee with a given id.
     * 
     * @params: path param id.
     */
    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@Valid @PathVariable String id) {
        this.employeeService.deleteEmployee(id);
    }

}
