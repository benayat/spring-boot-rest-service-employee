package com.hapoalim.employee.employee.repository;

import com.hapoalim.employee.employee.model.Employee;

import org.springframework.data.jpa.repository.JpaRepository;

/* 
this interface will give us all the crud methods we need to use with employee:
inheritance - it extends JpaRepository, for the functionality, so no need to implement
any of this myself.
<Employee> - domain type, and <Long> - Id type.

*/
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
