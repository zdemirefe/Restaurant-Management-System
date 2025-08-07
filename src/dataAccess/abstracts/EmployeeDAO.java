package dataAccess.abstracts;

import java.util.List;

import entities.Employee;

public interface EmployeeDAO {
	List<Employee> getAll();
	void createEmployeeData(Employee employee);
	void deleteEmployeeData(Employee employee);
}
