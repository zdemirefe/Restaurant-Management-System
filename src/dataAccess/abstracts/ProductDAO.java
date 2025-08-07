package dataAccess.abstracts;

import java.util.List;

import entities.Product;

public interface ProductDAO {
	List<Product> getAll();
	void createProductData(Product product);
	void deleteProduct(Product product);
}
