package com.crud.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.exception.ResourceNotFoundException;
import com.crud.model.Employee;
import com.crud.repo.EmployeeRepo;
import com.crud.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	
	@Autowired
	private EmployeeRepo employeeRepo;

	@Override
	public Employee saveEmployee(Employee employee) {
		Optional<Employee> savedEmployee = employeeRepo.findByEmail(employee.getEmail());
		if(savedEmployee.isPresent()) {
			throw new ResourceNotFoundException("Employee Already exist give email: " +employee.getEmail());
		}
		return employeeRepo.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		
		return employeeRepo.findAll();
	}

	@Override
	public Optional<Employee> getEmloyeeById(long id) {
		
		return employeeRepo.findById(id);
	}

	@Override
	public Employee updateEmployee(Employee updateEmployee) {
		
		return employeeRepo.save(updateEmployee);
	}

	@Override
	public void deleteEmployee(long id) {
		employeeRepo.deleteById(id);
		
	}
	
	

	

}
