package com.craig.liquibase;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc(print = MockMvcPrint.SYSTEM_OUT, printOnlyOnFailure = false)
@DBRider
class LiquibasePlaygroundApplicationTests {

	@Container
	@ServiceConnection
	static MSSQLServerContainer<?> sqlServerContainer = new MSSQLServerContainer<>(
			DockerImageName.parse("mcr.microsoft.com/mssql/server:latest")
	).acceptLicense();

    @Autowired
    private MockMvc mockMvc;

	@Test
	@DataSet(value = "dataset/2-employees.yaml")
	void getEmployees() throws Exception {
		mockMvc.perform(get("/employees"))
			   .andExpect(status().isOk());
	}

	@Test
	void getEmployeesEmpty() throws Exception {
		mockMvc.perform(get("/employees"))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$").isEmpty());
	}

}
