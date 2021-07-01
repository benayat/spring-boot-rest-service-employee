package com.hapoalim.employee.employee.controller;

import java.util.List;

import javax.validation.Valid;

import com.hapoalim.employee.employee.repository.EmployeeRepository;
import com.hapoalim.employee.employee.exception.EmployeeNotFoundException;
import com.hapoalim.employee.employee.model.Employee;

/* problem with spring hetaoas - it didn't recognize linkTo and methodOn methods, 
so I had to go with a walkaround:"https://stackoverflow.com/questions/53869415/the-sts-couldnt-understand-my-hateoas-import-and-report-error" */
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
/* 
telling java to treat this as a controller - which has mappings, and parsed as responseBody.
the controller has a repository attribute, where we keep all the employees.
the mappings and methods are simple, nothing to add.
* to add validation to the rest requests, I'll just add @Valid before @RequestBody for each request.
*/

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/employees")
    List<Employee> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/employees")
    Employee newEmployee(@Valid @RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    // Single item

    @GetMapping("/employees/{id}")
    EntityModel<Employee> one(@Valid @PathVariable Long id) {

        Employee employee = repository.findById(id) //
                .orElseThrow(() -> new EmployeeNotFoundException());

        return EntityModel.of(employee, //
                linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
    }

    // orElseGet - reminds me of pythons while-else. in this case - return a value,
    // and if it's not here
    // - orElseGet...
    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@Valid @RequestBody Employee newEmployee, @PathVariable Long id) {

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
    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@Valid @PathVariable Long id) {
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