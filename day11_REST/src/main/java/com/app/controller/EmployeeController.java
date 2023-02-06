package com.app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ApiResponse;
import com.app.dto.EmployeeResponse;
import com.app.dto.LoginRequestDto;
import com.app.pojos.Employee;
import com.app.service.EmployeeService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService empService;

	public EmployeeController() {
		System.out.println("in def ctor " + getClass());
	}


	@GetMapping
	public List<Employee> getAllEmps() {
		System.out.println("in get all emps");
		return empService.getAllEmpDetails();
	}
	@PostMapping
	public Employee saveEmpDetails(@RequestBody Employee transientEmp) {
		System.out.println("in save emp " + transientEmp + " id " + transientEmp.getId());//
		return empService.addEmpDetails(transientEmp);

	}

	@DeleteMapping("/{empId}")
	public ApiResponse deleteEmpDetails(@PathVariable Long empId) {
		System.out.println("in del emp " + empId);
		return new ApiResponse(empService.deleteEmpDetails(empId));
	}

	@GetMapping("/{empId}")
	public Employee getEmpDetails(@PathVariable Long empId) {
		System.out.println("in get emp details " + empId);
		return empService.fetchEmpDetails(empId);
	}

	
	@PutMapping
	public Employee updateEmpDetails(@RequestBody Employee detachedEmp) {
		System.out.println("in update emp " + detachedEmp.getId());// not null
		return empService.updateEmpDetails(detachedEmp);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> validateEmployee(@RequestBody LoginRequestDto dto) {
		//System.out.println("in emp signin " + dto);
		try {
			return ResponseEntity.ok(empService.authenticateEmp(dto));
		} catch (RuntimeException e) {
			System.out.println("err in emp controller " + e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body(new ApiResponse(e.getMessage()));
		}

	}

	
	@GetMapping("/date/{joinDate1}/dept/{dept1}")
	public List<Employee> getAllEmpsByDateAndDept(
			@PathVariable @DateTimeFormat(pattern = "yyyy-M-d") LocalDate joinDate1, @PathVariable String dept1) {
		System.out.println("in get all emps by date n dept " + joinDate1 + " " + dept1);
		return empService.getEmpsByDateAndDept(joinDate1, dept1);
	}

	
	@GetMapping("/salary")
	public List<EmployeeResponse> getAllEmpsBySalaryRange(@RequestParam double minSal, double maxSal) {
		System.out.println("in get emps by sal " + minSal + " " + maxSal);
		return empService.getEmpsBySalary(minSal, maxSal);
	}

	

}
