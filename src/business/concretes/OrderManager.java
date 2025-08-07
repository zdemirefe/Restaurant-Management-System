package business.concretes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import business.abstracts.OrderService;
import dataAccess.abstracts.OrderDAO;
import entities.Order;
import entities.Product;

public class OrderManager implements OrderService{
	private OrderDAO orderDAO;

	public OrderManager(OrderDAO orderDAO) {
		super();
		this.orderDAO = orderDAO;
	}

	@Override
	public Order createOrder(int tableId, List<Product> products, int employeeId) {
		double totalPrice=0;
		for (Product product : products) {
			totalPrice = product.getPrice() + totalPrice; 
		}
		Order order = new Order();
		order.setTotalPrice(totalPrice);
		order.setProducts(products);
		order.setTableId(tableId);
		LocalTime now = LocalTime.now();
		LocalDate date = LocalDate.now();
		String year = String.valueOf(date.getYear());
		String month;
		if (date.getMonthValue()<10) {
			month = "0" + String.valueOf(date.getMonthValue());
		}
		else {
			month = String.valueOf(date.getMonthValue());
		}
		String day = String.valueOf(date.getDayOfMonth());
		String hour = String.valueOf(now.getHour());
		String minute = String.valueOf(now.getMinute());
		String waiterId;
		if (employeeId<10) {
			waiterId = "00" + String.valueOf(employeeId);
		}
		else if (10<=employeeId && employeeId<100){
			waiterId = "0" +String.valueOf(employeeId);
		}
		else {
			waiterId = String.valueOf(employeeId);
		}
		String orderIdTemp = year + month + day + hour + minute + waiterId;
		long orderId = Long.valueOf(orderIdTemp);
		order.setOrderId(orderId);
		order.setEmployeeId(employeeId);
		return order;
	}

	@Override
	public Order getOrderById(int orderId) {
		List<Order> orders = orderDAO.getAll();
		Order selected_order = new Order();
		for (Order order : orders) {
			if(order.getOrderId()==orderId) {
				selected_order = order;
			}
		}
		return selected_order;
	}

	@Override
	public List<Order> getAllOrders() {
		List<Order> allOrders = orderDAO.getAll();
		return allOrders;
	}


	@Override
	public Order closeOrder(Order order) {
		orderDAO.createOrderData(order);
		return order;
	}

	@Override
	public List<Order> getAllOrdersByDay(String year_month_day) {
		List<Order> allOrders = getAllOrders();
		List<Order> selectedOrders = new ArrayList<>();
		for (Order order : allOrders) {
			if(String.valueOf(order.getOrderId()).substring(0, 8).equals(year_month_day)) 
			{
				selectedOrders.add(order);
			}
		}
		return selectedOrders;
	}
	
}
