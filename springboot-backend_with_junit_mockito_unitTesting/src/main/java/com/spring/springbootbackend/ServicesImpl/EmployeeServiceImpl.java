package com.spring.springbootbackend.ServicesImpl;

import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.springbootbackend.Model.Employee;
import com.spring.springbootbackend.Repository.EmployeeRepository;
import com.spring.springbootbackend.Services.EmployeeService;
import com.spring.springbootbackend.exception.EmployeeException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public EmployeeServiceImpl() {
		super();

	}

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Override
	public List<Employee> getAllEmployees() throws EmployeeException{

		return employeeRepository.findAll();

	}

	@Override
	public Employee addEmployee(Employee employee) throws EmployeeException{

		return employeeRepository.saveAndFlush(employee);

	}  

	@Override
	public Employee updateEmployee(Employee employee, Long id) throws EmployeeException {

		Employee ExistingEmployee = employeeRepository.findById(id).get();

		ExistingEmployee.setFirstName(employee.getFirstName());
		ExistingEmployee.setLastName(employee.getLastName());
		ExistingEmployee.setEmailId(employee.getEmailId());

		Employee upadtedEmpData = employeeRepository.save(ExistingEmployee);

		return upadtedEmpData;

	}

	@Override
	public String deleteEmployeeById(Long id) throws EmployeeException {

		try {
			for (Employee i : employeeRepository.findAll()) {
				if (i.getId().equals(id)) {

					employeeRepository.deleteById(i.getId());
					return "employee with id : " + id + " has been deleted";
				}

			}

		} catch (Exception e) {
			throw new EmployeeException("Employee details with given Id : " + id + " not found");
		}
		return "employee with id : " + id + " has been deleted";
	}

	@Override
	public Employee getEmpById(Long id) throws EmployeeException {

		Employee bean = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeException("Employee details with given user id : " + id + " not found"));

		return bean;

	}

	@Override
	public Employee getEmpByEmailId(String emailId) throws EmployeeException {

		Employee emp = null;
		try {

			if (emailId != null) {

				emp = employeeRepository.findByEmailId(emailId);
			}

		} catch (Exception e) {
			throw new EmployeeException("employee not found");
		}

		return emp;

	}

	@Override
	public Employee loginEmployee(Long employeeId, String password) throws EmployeeException {

		Employee emp = null;

		try {
			if (employeeId != null && password != null) {
				emp = employeeRepository.findByEmployeeIdAndPassword(employeeId, password);
			}
		} catch (Exception e) {
			throw new EmployeeException(
					"Employee employeeId :  " + employeeId + " and password : " + password + "  is incorrect ");
		}

		return emp;
	}

	@Override
	public String authenticate(Employee employee) throws EmployeeException {
		Employee emp = null;
		try {
			emp = employeeRepository.findById(employee.getEmployeeId()).get();
			for (Employee e : employeeRepository.findAll()) {
				if (emp.getEmployeeId().equals(e.getEmployeeId())) {
					if (emp.getPassword().equals(e.getPassword())) {
						return "Authentication Successfull";
					}
					return "Employee details not found! Invalid details";
				}
			}
		} catch (Exception e) {
			throw new EmployeeException("Employee details not found! Invalid details");
		}

		return null;

	}

	@Override
	public Employee changePassword(Employee employee, Long id) throws EmployeeException {

		Employee exisuser = employeeRepository.findById(id).orElseThrow(
				() -> new EmployeeException("Employee id : " + id + "  is incorrect! Please Enter correct id"));

		exisuser.setPassword(employee.getPassword());
		exisuser.setConfirmPassword(employee.getConfirmPassword());

		employeeRepository.save(exisuser);
		return exisuser;

	}

	@Override
	public Employee forgotPassword(String emailId) throws EmployeeException {
		return getEmpByEmailId(emailId);
	}

	@Override
	public Employee get_Employee_By_firstName(String firstName) throws EmployeeException {

		return employeeRepository.findByFirstName(firstName);

	}

	@Override
	public Employee getEmpByEmployeeId(Long employeeId) throws EmployeeException {

		return employeeRepository.findByEmployeeId(employeeId);
	}

}
