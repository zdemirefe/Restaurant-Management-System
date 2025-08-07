package business.concretes;

import java.util.Comparator;
import java.util.List;

import business.abstracts.EmployeeService;
import dataAccess.abstracts.EmployeeDAO;
import entities.Employee;

public class EmployeeManager implements EmployeeService {

	private EmployeeDAO employeeDAO;

	public EmployeeManager(EmployeeDAO employeeDAO) {
		super();
		this.employeeDAO = employeeDAO;
	}

	@Override
	public Employee getEmployeeById(int employeeId) {
		Employee selected_employee = new Employee();
		List<Employee> employeeList = getAllEmployees();
		for (Employee employee : employeeList) {
			if (employee.getEmployeeId() == employeeId) {
				selected_employee = employee;
			}
		}
		return selected_employee;
	}

	@Override
	public List<Employee> getAllEmployees() {
		Comparator<Employee> employeeIdComparator = Comparator.comparing(Employee::getEmployeeId);
		List<Employee> employeeList = employeeDAO.getAll();
		employeeList.sort(employeeIdComparator);
		return employeeList;
	}

	@Override
	public Employee createEmployee(String employeeName, String employeeSurname) {
		Employee createEmployee = new Employee();
		int employeeId = getAllEmployees().size() + 1;
		createEmployee.setEmployeeId(employeeId);
		createEmployee.setEmployeeName(employeeName);
		createEmployee.setEmployeeSurname(employeeSurname);
		employeeDAO.createEmployeeData(createEmployee);
		return createEmployee;
	}

	@Override
	public void deleteEmployee(Employee employee) {
		int lastEmployeeId = getAllEmployees().size();
		Employee deleteEmployee = employee;
		Employee lastEmployee = getEmployeeById(lastEmployeeId);
		int tempEmployeeId = employee.getEmployeeId();
		employeeDAO.deleteEmployeeData(lastEmployee);
		employeeDAO.deleteEmployeeData(deleteEmployee);
		if (lastEmployee.getEmployeeId() != deleteEmployee.getEmployeeId()) {
			lastEmployee.setEmployeeId(tempEmployeeId);
			employeeDAO.createEmployeeData(lastEmployee);
		}
	}
}
