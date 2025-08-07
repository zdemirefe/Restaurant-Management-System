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

import dataAccess.abstracts.ProductDAO;
import entities.Product;

public class ProductDaoManager implements ProductDAO {

	@Override
	public List<Product> getAll() {
		String filePath = "/Users/efe/eclipse-workspace/OOP Project/src/product.txt";
		String line;
		List<Product> products = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			while ((line = br.readLine()) != null) {

				String[] values = line.split(",");
				Product product = new Product();
				product.setProductId(Integer.valueOf(values[0]));
				product.setName(values[1]);
				product.setPrice(Double.valueOf(values[2]));
				products.add(product);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public void createProductData(Product product) {
	    String filePath = "/Users/efe/eclipse-workspace/OOP Project/src/product.txt";
	    try {
	        Path path = Paths.get(filePath);
	        List<String> fileContent = Files.readAllLines(path, StandardCharsets.UTF_8);

	        String productId = String.valueOf(product.getProductId());
	        String productName = product.getName();
	        String productPrice = String.valueOf(product.getPrice());
	        String newProductLine = productId + "," + productName + "," + productPrice;

	        fileContent.add(newProductLine);

	        Files.write(path, fileContent, StandardCharsets.UTF_8);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


	@Override
	public void deleteProduct(Product product) {
	    String filePath = "/Users/efe/eclipse-workspace/OOP Project/src/product.txt";
	    String deleteProduct = String.valueOf(product.getProductId()) + "," + product.getName() + "," + String.valueOf(product.getPrice());

	    try {
	        Path path = Paths.get(filePath);
	        List<String> fileContent = Files.readAllLines(path, StandardCharsets.UTF_8);

	        List<String> updatedContent = fileContent.stream()
	                .filter(line -> !line.equals(deleteProduct))
	                .collect(Collectors.toList());

	        Files.write(path, updatedContent, StandardCharsets.UTF_8);

	    } catch (IOException e) {
	        System.out.println("Dosya işlemleri sırasında bir hata oluştu.");
	        e.printStackTrace();
	    }
	}

}
