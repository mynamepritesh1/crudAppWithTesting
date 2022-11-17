package com.crud.service;

import java.util.List;
import java.util.Optional;

import com.crud.model.Employee;

public interface EmployeeService {

	public Employee saveEmployee(Employee employee);

	public List<Employee> getAllEmployees();

	Optional<Employee> getEmloyeeById(long id);

	public Employee updateEmployee(Employee updateEmployee);

	public void deleteEmployee(long id);

	
		
		
	

}
