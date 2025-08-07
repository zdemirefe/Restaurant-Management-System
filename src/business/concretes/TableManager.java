package business.concretes;

import java.util.Comparator;
import java.util.List;

import business.abstracts.TableService;
import dataAccess.abstracts.TableDAO;
import entities.Table;

public class TableManager implements TableService {

	private TableDAO tableDAO;

	public TableManager(TableDAO tableDAO) {
		super();
		this.tableDAO = tableDAO;
	}

	@Override
	public Table createTable() {
		Table table = new Table();
		List<Table> countTables = tableDAO.getAll();
		int tableId = countTables.size() + 1;
		table.setTableId(tableId);
		table.setTableName("MASA " + String.valueOf(tableId));
		table.setReserved(false);
		tableDAO.createTableData(table);
		return table;
	}

	@Override
	public Table getTableById(int tableId) {
		List<Table> tables = getAllTables();
		Table selectedTable = new Table();
		for (Table table : tables) {
			if (tableId == table.getTableId()) {
				selectedTable = table;
			}
		}
		return selectedTable;
	}

	@Override
	public List<Table> getAllTables() {
		Comparator<Table> tableIdComparator = Comparator.comparing(Table::getTableId);
		List<Table> allTables = tableDAO.getAll();
		allTables.sort(tableIdComparator);
		return allTables;
	}

	@Override
	public void deleteTable(Table table) {
		int lastTableId = getAllTables().size();
		Table lastTable = getTableById(lastTableId);
		Table deleteTable = table;
		int tempTableId = table.getTableId();
		tableDAO.deleteTableData(deleteTable);
		tableDAO.deleteTableData(lastTable);
		if (deleteTable.getTableId() != lastTable.getTableId()) {
			lastTable.setTableId(tempTableId);
			tableDAO.createTableData(lastTable);
		}
	}

	@Override
	public void reservationTable(Table table,boolean isReserved) {
		table.setReserved(isReserved);
		tableDAO.reservationTable(table);
	}

}
