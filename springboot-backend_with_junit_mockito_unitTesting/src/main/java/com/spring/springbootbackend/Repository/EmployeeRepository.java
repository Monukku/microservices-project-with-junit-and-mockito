package com.spring.springbootbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.springbootbackend.Model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	public Employee findByFirstName(String firstName);

	public Employee findByEmailId(String emailId);

	public Employee findByEmployeeId(Long employeeId);

	public Employee findByEmployeeIdAndPassword(Long employeeId, String password);
	

}
