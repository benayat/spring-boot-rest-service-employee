package com.hapoalim.employee.employee.service;

import java.util.List;

import javax.validation.Valid;

import com.hapoalim.employee.employee.exception.EmployeeNotFoundException;
import com.hapoalim.employee.employee.model.Employee;
import com.hapoalim.employee.employee.repository.EmployeeRepository;

import org.springframework.data.domain.PageRequest;

/* 
employee service - a class to handle all bussiness logic.
in the controller, I'll just handle the requests, and send those here, to keep the controller
clean and readable for interacting by HTTP requests.
- note - I had to change the methods access level to public, so I can access them from the controller.
* to add validation to the rest requests, I'll just add @Valid before the relevant parameter name.

*/
public class EmployeeService {

    private final EmployeeRepository repository;

    // constructor - just populates the member field repository.
    EmployeeService(EmployeeRepository repository) {
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
    public Iterable<Employee> getAll() {
        return repository.findAll();
    }

    /*
     * method: getByName: returns all employees by the given name. this method is
     * "free" with the help of elasticsearch, no need to implement.
     * 
     * @params: name - a query parameter.
     * 
     * @validation: if it's null - it will throw IllegalArgumentException.
     * 
     * @return: the employees are found, or the custom error
     * EmployeeNotFoundException.
     */
    public List<Employee> getByName(@Valid String name) {
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
    public Employee getOne(@Valid String id) {
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
    public Employee addNewEmployee(@Valid Employee newEmployee) {
        return repository.save(newEmployee);
    }

    /*
     * method: replaceEmployee: PUT method to update an employee, or create a new
     * one if he doesn't exist.
     * 
     * @params: id - path parameter.
     * 
     * @return: the new/updated employee.
     */
    public Employee replaceEmployee(@Valid Employee newEmployee, String id) {
        return repository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setRole(newEmployee.getRole());
            return repository.save(employee);
        }).orElseGet(() -> {
            newEmployee.setId(id);
            return repository.save(newEmployee);
        });
    }

    /*
     * delete an employee by given id.
     * 
     */
    public void deleteEmployee(@Valid String id) {
        repository.deleteById(id);
    }
}
