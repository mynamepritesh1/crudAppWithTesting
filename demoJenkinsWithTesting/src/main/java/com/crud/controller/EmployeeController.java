package com.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.crud.model.Employee;
import com.crud.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	
	
	@Autowired
	private EmployeeService employeeService;
	
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Employee createEmployee(@RequestBody Employee employee) {
		
		return employeeService.saveEmployee(employee);
		
	}
	
	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public List<Employee> getAllEmployees(){
		return  employeeService.getAllEmployees();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmloyeeById(@PathVariable("id") long employeeId){
		return employeeService.getEmloyeeById(employeeId).map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmloyeeById(@PathVariable("id") long employeeId,@RequestBody Employee employee){
		return employeeService.getEmloyeeById(employeeId).map(savedEmployee ->{
			savedEmployee.setFirstName(employee.getFirstName());
			savedEmployee.setLastName(employee.getLastName());
			savedEmployee.setEmail(employee.getEmail());
			
			Employee updateEmployee = employeeService.updateEmployee(savedEmployee);
			
			return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
		}).orElseGet(()-> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") long employeeId){
		employeeService.deleteEmployee(employeeId);
		return new ResponseEntity<String>("Employee Deleted Successfully", HttpStatus.OK);
	}
	
	

}

