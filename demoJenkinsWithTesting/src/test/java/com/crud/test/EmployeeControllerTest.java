package com.crud.test;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.crud.model.Employee;
import com.crud.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;






@WebMvcTest
public class EmployeeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EmployeeService employeeService;
	
	
	@Autowired
	private ObjectMapper mapper;
	

	@Test
	public void testCreateEmployee()throws Exception{
		Employee employee = new Employee(10,"ayush", "ingale","ayush@gmail.com");
		when(employeeService.saveEmployee(ArgumentMatchers.any())).thenReturn(employee);
		
		ObjectMapper mapper = new ObjectMapper();
		String employeeJson = mapper.writeValueAsString(employee);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.
				post("/api/create").contentType(MediaType.APPLICATION_JSON).
				content(employeeJson);
		ResultActions perform = mockMvc.perform(requestBuilder);
		MvcResult andReturn = perform.andReturn();
		MockHttpServletResponse response = andReturn.getResponse();
		int status = response.getStatus();
		assertEquals(201, status);
	}
	
	@Test
	public void testgetAllEmployees() throws Exception{
		List<Employee> allEmployees = new ArrayList<>();
		allEmployees.add(Employee.builder().firstName("ayush").lastName("ingale").email("ayush@gmail.com").build());
        when(employeeService.getAllEmployees()).thenReturn(allEmployees);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/");
		ResultActions perform = mockMvc.perform(requestBuilder);
		MvcResult andReturn = perform.andReturn();
		MockHttpServletResponse response = andReturn.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}
	
	//get employeebyId
	@Test
	public void testgetEmloyeeById() throws Exception{
		
//		long employeeId =1L;
//		
//		Employee employee = new Employee(10,"ayush", "ingale","ayush@gmail.com");
//		when(employeeService.saveEmployee(ArgumentMatchers.any())).thenReturn(employee);
//		
//		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/{id}", employeeId);
//		ResultActions perform = mockMvc.perform(requestBuilder);
//		MvcResult andReturn = perform.andReturn();
//		MockHttpServletResponse response = andReturn.getResponse();
//		int status = response.getStatus();
//		assertEquals(200, status);
		
		long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();
        when(employeeService.getEmloyeeById(employeeId)).thenReturn(Optional.of(employee));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    // negative scenario - valid employee id
    // JUnit test for GET employee by id REST API
    @Test
    public void testgetEmloyeeByIdInvalidId() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();
        when(employeeService.getEmloyeeById(employeeId)).thenReturn(Optional.empty());

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }
    // JUnit test for update employee REST API - positive scenario
        @Test
        public void testUpdateEmployeepositive() throws Exception{
            // given - precondition or setup
            long employeeId = 1L;
            Employee savedEmployee = Employee.builder()
                    .firstName("Ramesh")
                    .lastName("Fadatare")
                    .email("ramesh@gmail.com")
                    .build();

            Employee updatedEmployee = Employee.builder()
                    .firstName("Ram")
                    .lastName("Jadhav")
                    .email("ram@gmail.com")
                    .build();
            when(employeeService.getEmloyeeById(employeeId)).thenReturn(Optional.of(savedEmployee));
//            given(employeeService.updateEmployee(any(Employee.class)))
//                    .willAnswer((invocation)-> invocation.getArgument(0));

            // when -  action or the behaviour that we are going test
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/{id}",employeeId);
    		ResultActions perform = mockMvc.perform(requestBuilder);
    		MvcResult andReturn = perform.andReturn();
    		MockHttpServletResponse response = andReturn.getResponse();
    		int status = response.getStatus();
    		assertEquals(200, status);
        }

    // JUnit test for update employee REST API - negative scenario
    @Test
    public void testUpdateEmployeenegativ() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Ram")
                .lastName("Jadhav")
                .email("ram@gmail.com")
                .build();
        when(employeeService.getEmloyeeById(employeeId)).thenReturn(Optional.empty());
//        given(employeeService.updateEmployee(any(Employee.class)))
//                .willAnswer((invocation)-> invocation.getArgument(0));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedEmployee)));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

// JUnit test for delete employee REST API
    @Test
    public void testDeleteEmployee() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
//        willDoNothing().given(employeeService).deleteEmployee(employeeId);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
		
	}


	
	




 
