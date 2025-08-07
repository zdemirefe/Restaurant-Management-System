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

import dataAccess.abstracts.TableDAO;
import entities.Table;

public class TableDaoManager implements TableDAO {

	@Override
	public List<Table> getAll() {
		String filePath = "/Users/efe/eclipse-workspace/OOP Project/src/table.txt";
		String line;
		List<Table> tables = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			while ((line = br.readLine()) != null) {

				String[] values = line.split(",");
				Table table = new Table();
				table.setTableId(Integer.valueOf(values[0]));
				table.setTableName(values[1]);
				table.setReserved(Boolean.valueOf(values[2]));
				tables.add(table);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return tables;
	}

	@Override
	public void createTableData(Table table) {
		String filePath = "/Users/efe/eclipse-workspace/OOP Project/src/table.txt";
		try {
			Path path = Paths.get(filePath);
			List<String> fileContent = Files.readAllLines(path, StandardCharsets.UTF_8);

			String tableId = String.valueOf(table.getTableId());
			String tableName = table.getTableName();
			String isReserved = String.valueOf(table.isReserved());
			String newTableLine = tableId + "," + tableName + "," + isReserved;

			fileContent.add(newTableLine);

			Files.write(path, fileContent, StandardCharsets.UTF_8);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteTableData(Table table) {
		String filePath = "/Users/efe/eclipse-workspace/OOP Project/src/table.txt";
		String deleteTable = String.valueOf(table.getTableId()) + "," + table.getTableName();

		try {
			Path path = Paths.get(filePath);
			List<String> fileContent = Files.readAllLines(path, StandardCharsets.UTF_8);

			List<String> updatedContent = fileContent.stream().filter(line -> !line.equals(deleteTable))
					.collect(Collectors.toList());

			Files.write(path, updatedContent, StandardCharsets.UTF_8);

		} catch (IOException e) {
			System.out.println("Dosya işlemleri sırasında bir hata oluştu.");
			e.printStackTrace();
		}
	}

	@Override
	public void reservationTable(Table table) {
		String filePath = "/Users/efe/eclipse-workspace/OOP Project/src/table.txt";
		String updateTable = String.valueOf(table.getTableId()) + "," + table.getTableName() + ","
				+ String.valueOf(table.isReserved());
		try {
			Path path = Paths.get(filePath);
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			List<String> updatedLines = lines.stream().map(line -> {
				String[] parts = line.split(",");
				if (parts[0].equals(String.valueOf(table.getTableId()))) {
					return updateTable;
				} else {
					return line;
				}
			}).collect(Collectors.toList());
			Files.write(path, updatedLines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Dosya işlemleri sırasında bir hata oluştu.");
			e.printStackTrace();
		}
	}

}
