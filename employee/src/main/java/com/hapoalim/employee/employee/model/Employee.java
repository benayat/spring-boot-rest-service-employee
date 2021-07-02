package com.hapoalim.employee.employee.model;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "employee")
public class Employee {
    // @id - primary key. generated value - support generating value for the key.
    @Size(min = 1, max = 70, message = "please choose a role with a reasonable length")
    private @Id @GeneratedValue String id;
    @NotEmpty(message = "please provide a name")
    @Size(min = 1, max = 30, message = "please choose a name with a reasonable length")
    private String name;
    @NotEmpty(message = "please provide a role")
    @Size(min = 1, max = 70, message = "please choose a role with a reasonable length")
    private String role;

    // I had to make the constructors public, in order to use them from their
    // sibling packages.
    // empty constructor

    public Employee() {
    }

    public Employee(String name, String role) {
        this.name = name;
        this.role = role;
    }

    // all g';etters and setters - for id, name and role.
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getRole() {
        return this.role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // overiding the default comparison operator, because it's a complicated object.
    @Override
    public boolean equals(Object o) {
        // true if it's the same object, with normal comparison.
        if (this == o)
            return true;
        if (!(o instanceof Employee))
            return false;
        Employee employee = (Employee) o;
        return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name)
                && Objects.equals(this.role, employee.role);
    }

    // hash the object, using the 3 values of id, name and role.
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.role);
    }

    // overiding for a unique way to print the object with a simple print call.
    @Override
    public String toString() {
        return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}';
    }
}
