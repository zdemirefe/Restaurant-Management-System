package dataAccess.abstracts;

import java.util.List;

import entities.Table;

public interface TableDAO {
	List<Table> getAll();
	void createTableData(Table table);
	void deleteTableData(Table table);
	void reservationTable(Table table);
}
