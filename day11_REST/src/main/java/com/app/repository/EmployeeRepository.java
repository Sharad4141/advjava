package com.app.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.pojos.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Optional<Employee> findByEmailAndPassword(String em, String pass);

	
	List<Employee> findByJoinDateAfterAndDepartment(LocalDate date, String deptName);

	@Query(value = "select new com.app.pojos.Employee(firstName,lastName) from Employee e where e.salary between ?1 and ?2")
	
	Stream<Employee> fetchEmpNamesBySalaryRange(double minSalary, double maxSalary);

	
    //Optional<Employee> fetchEmpNamesBySalaryRange(double minSal, double maxSal);
}
