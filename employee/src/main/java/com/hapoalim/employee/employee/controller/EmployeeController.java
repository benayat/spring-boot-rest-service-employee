package com.hapoalim.employee.employee.controller;

import java.util.List;

import javax.validation.Valid;

import com.hapoalim.employee.employee.repository.EmployeeRepository;
import com.hapoalim.employee.employee.exception.EmployeeNotFoundException;
import com.hapoalim.employee.employee.model.Employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;

/* problem with spring hetaoas - it didn't recognize linkTo and methodOn methods, 
so I had to go with a walkaround:"https://stackoverflow.com/questions/53869415/the-sts-couldnt-understand-my-hateoas-import-and-report-error" */

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/* 
telling java to treat this as a controller - which has mappings, and parsed as responseBody.
the controller has a repository attribute, where we keep all the employees.
the mappings and methods are simple, nothing to add.
* to add validation to the rest requests, I'll just add @Valid before @RequestBody for each request.
*/

@RestController
public class EmployeeController {
    private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeRepository repository;

    // constructor - just populates the member field repository.
    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
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
    Iterable<Employee> getAll() {
        Iterable<Employee> allEmployees = repository.findAll();
        return allEmployees;

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
        return repository.findByEmployeeNameUsingCustomQuery(name, PageRequest.of(0, 10));
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
        return repository.findById(id) //
                .orElseThrow(() -> new EmployeeNotFoundException());

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
        logger.info("adding a new employee");
        return repository.save(newEmployee);
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
        logger.info("replacing employee " + newEmployee);
        return repository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setRole(newEmployee.getRole());
            return repository.save(employee);
        }).orElseGet(() -> {
            newEmployee.setId(id);
            return repository.save(newEmployee);
        });
    }

    // query params are inside {} in java.
    /*
     * delete an employee with a given id.
     * 
     * @params: path param id.
     */
    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@Valid @PathVariable String id) {
        repository.deleteById(id);
    }

}

/*
 * JPA/repository methods I learned so far: deleteById(id) save(Employee) - just
 * like mongodb.
 * 
 * 
 * I actually think that this entity/domain and JPA is amazing. really. you can
 * actually use SQL as document based database, and all that with a native
 * implementation inside java! couldn't be more convenient.
 */