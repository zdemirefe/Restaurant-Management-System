package dataAccess.concrete;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dataAccess.abstracts.EmployeeDAO;
import entities.Employee;

public class EmployeeDaoManager implements EmployeeDAO {

	@Override
	public List<Employee> getAll() {
		String filePath = "/Users/efe/eclipse-workspace/OOP Project/src/employee.txt";
		String line;
		List<Employee> employees = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			while ((line = br.readLine()) != null) {

				String[] values = line.split(",");
				Employee employee = new Employee();
				employee.setEmployeeId(Integer.valueOf(values[0]));
				employee.setEmployeeName(values[1]);
				employee.setEmployeeSurname(values[2]);
				employees.add(employee);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return employees;
	}

	@Override
	public void createEmployeeData(Employee employee) {
	    String filePath = "/Users/efe/eclipse-workspace/OOP Project/src/employee.txt";
	    try {
	        Path path = Paths.get(filePath);
	        List<String> fileContent = Files.readAllLines(path, StandardCharsets.UTF_8);

	        String employeeId = String.valueOf(employee.getEmployeeId());
	        String employeeName = employee.getEmployeeName();
	        String employeeSurname = employee.getEmployeeSurname();
	        String newEmployeeLine = employeeId + "," + employeeName + "," + employeeSurname;

	        fileContent.add(newEmployeeLine);

	        Files.write(path, fileContent, StandardCharsets.UTF_8);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


	@Override
	public void deleteEmployeeData(Employee employee) {
	    String filePath = "/Users/efe/eclipse-workspace/OOP Project/src/employee.txt";
	    String deleteEmployee = String.valueOf(employee.getEmployeeId()) + "," + employee.getEmployeeName() + "," + employee.getEmployeeSurname();

	    try {
	        Path path = Paths.get(filePath);
	        List<String> fileContent = Files.readAllLines(path, StandardCharsets.UTF_8);

	        List<String> updatedContent = fileContent.stream()
	                .filter(line -> !line.equals(deleteEmployee))
	                .collect(Collectors.toList());

	        Files.write(path, updatedContent, StandardCharsets.UTF_8);

	    } catch (IOException e) {
	        System.out.println("Dosya işlemleri sırasında bir hata oluştu.");
	        e.printStackTrace();
	    }
	}


}
