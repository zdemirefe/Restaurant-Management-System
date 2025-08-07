package business.concretes;

import java.util.Comparator;
import java.util.List;

import business.abstracts.ProductService;
import dataAccess.abstracts.ProductDAO;
import entities.Product;

public class ProductManager implements ProductService {

	private ProductDAO productDAO;

	public ProductManager(ProductDAO productDAO) {
		super();
		this.productDAO = productDAO;
	}

	@Override
	public Product createProduct(String productName, double productPrice) {
		Product product = new Product();
		List<Product> products = productDAO.getAll();
		int productId = products.size() + 1;
		product.setProductId(productId);
		product.setName(productName);
		product.setPrice(productPrice);
		productDAO.createProductData(product);
		return product;
	}

	@Override
	public Product getProductById(int productId) {
		List<Product> products = productDAO.getAll();
		Product selectedProduct = new Product();
		for (Product product : products) {
			if (product.getProductId() == productId) {
				selectedProduct = product;
			}
		}
		return selectedProduct;
	}

	@Override
	public List<Product> getAllProducts() {
		Comparator<Product> productIdComparator = Comparator.comparing(Product::getProductId);
		List<Product> allProducts = productDAO.getAll();
		allProducts.sort(productIdComparator);
		return allProducts;
	}

	@Override
	public void deleteProduct(Product product) {
		int lastProductId = getAllProducts().size();
		Product lastProduct = getProductById(lastProductId);
		Product deleteProduct = product;
		int tempProductId = product.getProductId();
		productDAO.deleteProduct(lastProduct);
		productDAO.deleteProduct(deleteProduct);
		if (lastProduct.getProductId() != deleteProduct.getProductId()) {
			lastProduct.setProductId(tempProductId);
			productDAO.createProductData(lastProduct);
		}
	}

}
