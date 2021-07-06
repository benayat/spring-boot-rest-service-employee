package com.hapoalim.employee.employee.repository;

import java.util.List;

import com.hapoalim.employee.employee.model.Employee;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

// second try - with elasticsearch.

public interface EmployeeRepository extends ElasticsearchRepository<Employee, String> {

    List<Employee> findByRole(String role, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}}]}}")
    List<Employee> findByEmployeeNameUsingCustomQuery(String name, Pageable pageable);
}