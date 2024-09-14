package com.example.rqchallenge.controller;


import com.example.rqchallenge.dto.Employee;
import com.example.rqchallenge.dto.request.CreateEmployeeRequest;
import com.example.rqchallenge.employees.IEmployeeController;
import com.example.rqchallenge.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
public class EmployeeController implements IEmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
        log.info("Fetching all employees");
        return employeeService.getEmployees();
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) {
        ResponseEntity<List<Employee>> entity;
        if (!StringUtils.isEmpty(searchString)) {
            log.info("Fetching employees who's name contains {}",searchString);
             return employeeService.getEmployeesByName(searchString);
        }
        log.error("search string cannot be empty");
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        ResponseEntity<List<Employee>> entity;
        if (!StringUtils.isEmpty(id)) {
            log.info("Fetching employee with ID {}",id);
            return employeeService.getEmployeeById(id);
        } else {
            log.error("id cannot be empty");
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        log.info("Fetching highest salary of employee");
        ResponseEntity<Integer> responseEntity = employeeService.getHighestSalaryOfEmployees();
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        log.info("Fetching top ten highest salary employee names");
        return employeeService.getTopTenHighestEarningEmployeeNames();
    }

    @Override
    public ResponseEntity<Employee> createEmployee(@RequestBody CreateEmployeeRequest createEmployeeRequest) {
        if (validateCreateEmployeeRequest(createEmployeeRequest)) {
            return employeeService.createEmployee(createEmployeeRequest);
        } else {
            log.error("Request body is invalid");
            return ResponseEntity.badRequest().build();
        }
    }

    private boolean validateCreateEmployeeRequest(CreateEmployeeRequest createEmployeeRequest) {
        try {
            if (Objects.isNull(createEmployeeRequest) || StringUtils.isEmpty(createEmployeeRequest.getAge().toString()) ||
                    StringUtils.isEmpty(createEmployeeRequest.getName().toString()) ||
                    StringUtils.isEmpty(createEmployeeRequest.getSalary().toString())) {
                return false;
            }
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        if (!StringUtils.isEmpty(id)) {
            log.info("Deleting employee with id {}", id);
            return employeeService.deleteEmployeeById(id);
        }
        log.error("id cannot be empty");
        return ResponseEntity.badRequest().build();
    }
}
