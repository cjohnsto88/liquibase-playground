package com.craig.liquibase.controller;

import com.craig.liquibase.dao.EmployeeDao;
import com.craig.liquibase.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeesController.class)
class EmployeesControllerTest {

    @MockBean
    private EmployeeDao employeeDao;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getEmployees() throws Exception {
        when(employeeDao.getEmployees()).thenReturn(List.of(
            new Employee("one", "ONE"),
            new Employee("two", "TWO")
        ));

        // language=json
        String expectedJson = """
                [
                  {
                    "firstName":"one",
                    "lastName":"ONE"
                  },
                  {
                    "firstName":"two",
                    "lastName":"TWO"
                  }
                ]
                """;

        mockMvc.perform(get("/employees"))
               .andExpect(content().json(expectedJson))
               .andExpect(status().isOk());
    }
}