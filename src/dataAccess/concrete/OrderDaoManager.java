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

import dataAccess.abstracts.OrderDAO;
import entities.Order;
import entities.Product;

public class OrderDaoManager implements OrderDAO {

	@Override
	public List<Order> getAll() {
		String filePath = "/Users/efe/eclipse-workspace/OOP Project/src/order.txt";
		String line;
		List<Order> orders = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			while ((line = br.readLine()) != null ) {
				if(line.equals("")) 
				{
					System.out.println("Sipariş Yok");
					break;
				}
				String[] values = line.split(",");
				Order order = new Order();
				order.setOrderId(Long.valueOf(values[0]));
				List<Product> products = new ArrayList<>();
				String[] productsInOrder = values[1].split(":");
				for (String string : productsInOrder) {
					String[] productsAtrributes = string.split("-");
					Product product = new Product();
					product.setProductId(Integer.valueOf(productsAtrributes[0]));
					product.setName(productsAtrributes[1]);
					product.setPrice(Double.valueOf(productsAtrributes[2]));
					products.add(product);
				}
				order.setProducts(products);
				order.setTableId(Integer.valueOf(values[2]));
				order.setEmployeeId(Integer.valueOf(values[3]));
				order.setTotalPrice(Double.valueOf(values[4]));
				orders.add(order);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return orders;
	}

	@Override
	public void createOrderData(Order order) {
	    String filePath = "/Users/efe/eclipse-workspace/OOP Project/src/order.txt";
	    try {
	        Path path = Paths.get(filePath);
	        List<String> fileContent = Files.readAllLines(path, StandardCharsets.UTF_8);

	       
	        String orderId = String.valueOf(order.getOrderId());
	        List<Product> products = order.getProducts();
	        String orderProducts = products.stream()
	            .map(product -> product.getProductId() + "-" + product.getName() + "-" + product.getPrice())
	            .collect(Collectors.joining(":"));
	        String tableId = String.valueOf(order.getTableId());
	        String employeeId = String.valueOf(order.getEmployeeId());
	        String totalPrice = String.valueOf(order.getTotalPrice());
	        String newOrderLine = orderId + "," + orderProducts + "," + tableId + "," + employeeId + "," + totalPrice;

	        
	        fileContent.add(newOrderLine);

	        Files.write(path, fileContent, StandardCharsets.UTF_8);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


}
