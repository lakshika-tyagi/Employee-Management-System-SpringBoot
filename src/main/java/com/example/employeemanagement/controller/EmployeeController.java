
package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.Employee;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    // In-memory storage
    private Map<Integer, Employee> employees =
            new HashMap<>();

    // (b) Add new employee - POST
    @PostMapping
    public ResponseEntity<Employee> addEmployee(
            @RequestBody Employee employee) {

        if (employees.containsKey(employee.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .build();
        }

        employees.put(employee.getId(), employee);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employee);
    }

    // (c) Retrieve all employees - GET
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {

        return ResponseEntity.ok(
                new ArrayList<>(employees.values())
        );
    }

    // (c) Retrieve employee by ID - GET
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(
            @PathVariable int id) {

        Employee employee = employees.get(id);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(employee);
    }

    // (d) Update employee - PUT
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable int id,
            @RequestBody Employee updatedEmployee) {

        if (!employees.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        updatedEmployee.setId(id);

        employees.put(id, updatedEmployee);

        return ResponseEntity.ok(updatedEmployee);
    }

    // (e) Delete employee - DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable int id) {

        if (!employees.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        employees.remove(id);

        return ResponseEntity.noContent().build();
    }
}
