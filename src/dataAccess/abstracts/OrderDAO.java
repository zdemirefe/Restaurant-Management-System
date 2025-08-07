package dataAccess.abstracts;

import java.util.List;

import entities.Order;

public interface OrderDAO {
	public List<Order> getAll();
	void createOrderData(Order order);
}
