package net.javaguides.springboot.crudrestfulwebservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.crudrestfulwebservices.exception.ResourceNotFoundException;
import net.javaguides.springboot.crudrestfulwebservices.model.Employee;
import net.javaguides.springboot.crudrestfulwebservices.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/status/check")
	public String statusCheck() {
		return "Working fine.....status code 200";
	}
	
	//create get all employees api
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	// create employee
	@PostMapping("/employees")  //@RequestBody annotation is used to convert multiples inputs from Http request to class variable (i.e. employee) 
	public Employee createEmployee(@RequestBody Employee employee) {
		
		return employeeRepository.save(employee);
	}
	//get employee by id
	@GetMapping("/employees/{id}")  //@PathVariable annotation is used to bind the HTTP template variable (i.e. "id" here) to the method parameter (i.e. employeeId here)
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value="id") long employeeId) throws ResourceNotFoundException{
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee not found for the given id"));
		return ResponseEntity.ok().body(employee);
	}
	
	// update employee
	@PutMapping("employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value="id") long employeeId, @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee=employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setFirstName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getEmailId());
		employeeRepository.save(employee);
		return ResponseEntity.ok().body(employee);
		
		
	}
	// delete employee by id
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable(value="id") long employeeId) throws ResourceNotFoundException {
		Employee employee=employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		employeeRepository.deleteById(employeeId);
		return ResponseEntity.ok().build();
	}
	

}
