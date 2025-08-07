package business.abstracts;

import java.util.List;

import entities.Order;
import entities.Product;

public interface OrderService {
	Order createOrder(int tableNumber, List<Product> products, int employeeId);
    Order getOrderById(int orderId);
    List<Order> getAllOrders();
    Order closeOrder(Order order);
    List<Order> getAllOrdersByDay(String year_month_day);
}
