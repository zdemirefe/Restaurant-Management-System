package business.abstracts;

import java.util.List;

import entities.Product;

public interface ProductService {
	Product createProduct(String productName, double productPrice);
	Product getProductById(int productId);
	List<Product> getAllProducts();
	void deleteProduct(Product product);
}
