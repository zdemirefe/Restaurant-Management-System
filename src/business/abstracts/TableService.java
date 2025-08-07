package business.abstracts;

import java.util.List;

import entities.Table;

public interface TableService {
	Table createTable();
	Table getTableById(int tableId);
	List<Table> getAllTables();
	void deleteTable(Table table);
	void reservationTable(Table table,boolean isReserved);
}
