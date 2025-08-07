package business.abstracts;

import java.util.List;

import entities.Employee;

public interface EmployeeService {
	public List<Employee> getAllEmployees(); 
	public Employee getEmployeeById(int employeeId);
	public Employee createEmployee(String employeeName, String employeeSurname);
	void deleteEmployee(Employee employee);
}
