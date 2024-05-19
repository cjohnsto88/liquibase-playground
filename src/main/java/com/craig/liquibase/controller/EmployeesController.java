package com.craig.liquibase.controller;

import com.craig.liquibase.dao.EmployeeDao;
import com.craig.liquibase.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private final EmployeeDao employeeDao;

    public EmployeesController(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @GetMapping
    public List<Employee> getEmployees() {
        return employeeDao.getEmployees();
    }
}
