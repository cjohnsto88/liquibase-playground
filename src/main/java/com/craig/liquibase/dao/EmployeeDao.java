package com.craig.liquibase.dao;

import com.craig.liquibase.model.Employee;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDao {

    private final JdbcClient jdbcClient;

    public EmployeeDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Employee> getEmployees() {
        return jdbcClient.sql("SELECT firstName, lastName FROM Employees")
                         .query((rs, row) -> new Employee(
                                 rs.getString("firstName"),
                                 rs.getString("lastName")
                         ))
                         .list();
    }

}
