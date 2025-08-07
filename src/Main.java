import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import business.abstracts.EmployeeService;
import business.abstracts.OrderService;
import business.abstracts.ProductService;
import business.abstracts.TableService;
import business.concretes.EmployeeManager;
import business.concretes.OrderManager;
import business.concretes.ProductManager;
import business.concretes.TableManager;
import dataAccess.abstracts.EmployeeDAO;
import dataAccess.abstracts.OrderDAO;
import dataAccess.abstracts.ProductDAO;
import dataAccess.abstracts.TableDAO;
import dataAccess.concrete.EmployeeDaoManager;
import dataAccess.concrete.OrderDaoManager;
import dataAccess.concrete.ProductDaoManager;
import dataAccess.concrete.TableDaoManager;
import entities.Employee;
import entities.Order;
import entities.Product;
import entities.Table;

public class Main {

	public static void main(String[] args) {

		TableDAO tableDAO = new TableDaoManager();
		TableService tableService = new TableManager(tableDAO);
		OrderDAO orderDAO = new OrderDaoManager();
		OrderService orderService = new OrderManager(orderDAO);
		ProductDAO productDAO = new ProductDaoManager();
		ProductService productService = new ProductManager(productDAO);
		EmployeeDAO employeeDAO = new EmployeeDaoManager();
		EmployeeService employeeService = new EmployeeManager(employeeDAO);
		HashMap<Integer, Order> hashMap = new HashMap<>();
		List<Table> tables = tableDAO.getAll();
		for (Table table : tables) {
			hashMap.put(table.getTableId(), null);
		}

		try (Scanner scanner = new Scanner(System.in)) {

			while (true) {

				List<Integer> reservedTableNumbers = new ArrayList<>();
				List<Integer> hasCustomerTableNumbers = new ArrayList<>();
				List<Integer> emptyTable = new ArrayList<>();

				System.out.println("             ---------------------------------------------------");
				System.out.println("                           RESTAURANT MASALARI");
				System.out.println("             ---------------------------------------------------");
				System.out.println();

				int counterConsole = 0;
				for (Map.Entry<Integer, Order> entry : hashMap.entrySet()) {
					if (counterConsole % 2 == 0) {
						System.out
								.println(" --------------------------------------------------------------------------");
					}
					if ((entry.getValue() == null)
							&& (tableService.getTableById(entry.getKey()).isReserved() == false)) {
						System.out.print("| ");
						System.out.print(tableService.getTableById(entry.getKey()).getTableName() + " <---> Masa Boş ");
						emptyTable.add(entry.getKey());
					} else if ((entry.getValue() == null)
							&& (tableService.getTableById(entry.getKey()).isReserved() == true)) {
						System.out.print("| ");
						System.out.print(tableService.getTableById(entry.getKey()).getTableName() + " <---> Rezerve ");
						reservedTableNumbers.add(entry.getKey());
					} else {
						System.out.print("| ");
						System.out.print(tableService.getTableById(entry.getKey()).getTableName() + " <--> "
								+ entry.getValue().getOrderId() + " --> " + entry.getValue().getTotalPrice() + " ");
						hasCustomerTableNumbers.add(entry.getKey());
					}
					if (entry.getKey() % 2 == 0) {
						System.out.println();
					}
					counterConsole = counterConsole + 1;

				}
				System.out.println(" --------------------------------------------------------------------------");
				System.out.println();
				System.out.println("1. Sipariş Gir");
				System.out.println("2. Siparişe Ekle");
				System.out.println("3. Siparişi Sil");
				System.out.println("4. Ödeme Al");
				System.out.println("5. Yönetici Giriş");
				System.out.println("6. Çıkış");
				System.out.println();
				System.out.print("Seçiminizi Yapın: ");
				
				boolean isValid = false;
				int secim=0;
				while (!isValid) {
		            try {
		                secim = scanner.nextInt();
		                isValid = true;
		            } catch (Exception e) {
		                System.out.print("Geçersiz giriş. Lütfen bir tamsayı giriniz: ");
		                scanner.next();
		            }
		        }
	

				switch (secim) {
				case 1:
					int masaSecimi = 0;
					int count2 = 0;
					while (!emptyTable.contains(masaSecimi)) {
						System.out.print("Sipariş Girmek İstediğiniz Masa Numarasını Giriniz: ");
						masaSecimi = scanner.nextInt();
						if (emptyTable.contains(masaSecimi) == false) {
							count2 = count2 + 1;
							break;
						}
					}
					;
					if (count2 > 0) {
						System.out.println("Girdiğiniz Masa Boş Değil...");
						System.out.println();
						break;
					}
					System.out.print("Siparişi Alan İşçi Numarasını Giriniz: ");
					int isciNumarası = scanner.nextInt();
					System.out.println();
					System.out.println("----Ürünler----");
					List<Product> allProducts = productService.getAllProducts();
					List<Product> selectedProducts = new ArrayList<>();
					for (Product product : allProducts) {
						System.out.println(String.valueOf(product.getProductId()) + " --> " + product.getName() + " "
								+ String.valueOf(product.getPrice()));
					}
					int urunNumarası = 1;
					while (urunNumarası != 0) {
						System.out.print("Ürünün Numarasını Giriniz: ");
						urunNumarası = scanner.nextInt();
						Product product = productService.getProductById(urunNumarası);
						selectedProducts.add(product);

						System.out.println();
						System.out.println("Çıkmak için 0 giriniz.");
					}
					;
					Order newOrder = orderService.createOrder(masaSecimi, selectedProducts, isciNumarası);
					hashMap.put(masaSecimi, newOrder);
					System.out.println();
					break;
				case 2:
					int masaSecimiUpdate = 0;
					int counter = 0;
					while (!hasCustomerTableNumbers.contains(masaSecimiUpdate)) {
						System.out.print("Masa Numarasını Seçiniz: ");
						masaSecimiUpdate = scanner.nextInt();
						if (hasCustomerTableNumbers.contains(masaSecimiUpdate) == false) {
							counter = counter + 1;
							break;
						}
					}
					;
					if (counter > 0) {
						System.out.println("Girdiğiniz Masa'da Sipariş Yok...");
						System.out.println();
						break;
					}
					System.out.println();
					Order updateOrder = hashMap.get(masaSecimiUpdate);
					System.out.println("----Ürünler----");
					List<Product> allProductsUpdate = productService.getAllProducts();
					List<Product> selectedProductsUpdate = updateOrder.getProducts();
					double totalPriceUpdate = updateOrder.getTotalPrice();
					for (Product product : allProductsUpdate) {
						System.out.println(String.valueOf(product.getProductId()) + " --> " + product.getName() + " "
								+ String.valueOf(product.getPrice()));
					}
					int urunNumarasıUpdate = 1;
					while (urunNumarasıUpdate != 0) {
						System.out.print("Ürünün Numarasını Giriniz: ");
						urunNumarasıUpdate = scanner.nextInt();
						Product product = productService.getProductById(urunNumarasıUpdate);
						selectedProductsUpdate.add(product);
						totalPriceUpdate = totalPriceUpdate + product.getPrice();
						System.out.println("Çıkmak için 0 giriniz.");
					}
					;
					updateOrder.setProducts(selectedProductsUpdate);
					updateOrder.setTotalPrice(totalPriceUpdate);
					hashMap.put(masaSecimiUpdate, updateOrder);
					System.out.println();
					break;
				case 3:
					int deleteOrderTableNumber = 0;
					int count3 = 0;
					while (!hasCustomerTableNumbers.contains(deleteOrderTableNumber)) {

						System.out.print("Siparişi Silmek İstediğiniz Masa Numarasını Giriniz: ");
						deleteOrderTableNumber = scanner.nextInt();
						if (hasCustomerTableNumbers.contains(deleteOrderTableNumber) == false) {
							count3 = count3 + 1;
							break;
						}
					}
					;
					if (count3 > 0) {
						System.out.println("Seçtiğiniz Masa'da Sipariş Yok...");
						System.out.println();
						break;
					}
					hashMap.put(deleteOrderTableNumber, null);
					System.out.println(String.valueOf(deleteOrderTableNumber) + ". MASA'daki Sipariş Silinmiştir.");
					System.out.println();
					break;
				case 4:
					int paymentTable = 0;
					int countPay = 0;
					while (!hasCustomerTableNumbers.contains(paymentTable)) {
						System.out.print("Ödeme Alınacak Masa Numarasını Giriniz: ");
						paymentTable = scanner.nextInt();
						if (hasCustomerTableNumbers.contains(paymentTable) == false) {
							countPay = countPay + 1;
							break;
						}
					}
					if (countPay > 0) {
						System.out.println("Seçtiğiniz Masa'da Sipariş Yok..");
						System.out.println();
						break;
					}
					System.out.println();
					Order paymentOrder = hashMap.get(paymentTable);
					System.out.println("Toplam Tutar: " + paymentOrder.getTotalPrice() + " Türk Lirası");
					System.out.println("Bizi Tercih Ettiğiniz İçin Teşekkürler. Yine Bekleriz.");
					System.out.println();
					orderService.closeOrder(paymentOrder);
					hashMap.put(paymentTable, null);
					System.out.println();
					break;
				case 5:
					String kullaniciAdi = "";
					String sifre = "";
					int countVerify = 0;
					while (!(kullaniciAdi.equals("admin") && sifre.equals("admin"))) {
						if (countVerify > 0) {
							System.out.println("Girdiğiniz Bilgiler Yanlış.");
							System.out.println();
							break;
						}
						System.out.println("Yönetici Paneline Geciş Yapıldı.");
						System.out.print("Kullanıcı Adı: ");
						kullaniciAdi = scanner.next();
						System.out.print("Şifre: ");
						sifre = scanner.next();
						countVerify = countVerify + 1;
						if (kullaniciAdi.equals("admin") && sifre.equals("admin")) {
							countVerify = countVerify - 1;
						}
					}
					;
					if (countVerify > 0) {
						break;
					}

					boolean exit = true;
					while (exit) {

						System.out.println("------------------------------------------");
						System.out.println("     Yönetici Paneline Hoşgeldiniz.");
						System.out.println("------------------------------------------");
						System.out.println("1.İşçi Ekleme");
						System.out.println("2.İsçi Çıkarma");
						System.out.println("3.Ürün Ekleme");
						System.out.println("4.Ürün Çıkarma");
						System.out.println("5.Masa Ekleme");
						System.out.println("6.Masa Çıkarma");
						System.out.println("7.Günlük Ciro Raporu Görüntüle");
						System.out.println("8.Tüm İşçileri Görüntüle");
						System.out.println("9.Tüm Ürünleri Görüntüle");
						System.out.println("10.Tüm Siparişleri Görüntüle");
						System.out.println("11.Rezervasyon Ekle");
						System.out.println("12.Rezervasyon Sil");
						System.out.println("13.Çıkış");
						System.out.println();
						System.out.print("Seçiminizi Yapın: ");
						
						boolean isValid2 = false;
						int secimYonetici=0;
				        
				        while (!isValid2) {
				            try {
				                secimYonetici = scanner.nextInt();
				                isValid2 = true;
				            } catch (Exception e) {
				                System.out.print("Geçersiz giriş. Lütfen bir tamsayı giriniz: ");
				                scanner.next(); 
				            }
				        }
						
						
						
						switch (secimYonetici) {
						case 1:
							scanner.nextLine();
							System.out.print("Eklemek İstediğiniz İşçinin Adını Giriniz: ");
							String newEmployeeName = scanner.nextLine();
							System.out.print("Eklemek İstediğiniz İşçinin Soyadını Giriniz: ");
							String newEmployeeSurname = scanner.next();
							employeeService.createEmployee(newEmployeeName, newEmployeeSurname);
							int newEmployeeId = employeeService.getAllEmployees().size();
							System.out.println(String.valueOf(newEmployeeId) + " Numaralı " + newEmployeeName + " "
									+ newEmployeeSurname + " işçisi eklendi.");
							break;
						case 2:
							System.out.print("İşten Çıkarmak İstediğiniz İşçinin Numarasını Giriniz: ");
							int deleteEmployeeId = scanner.nextInt();
							Employee deleteEmployee = employeeService.getEmployeeById(deleteEmployeeId);
							employeeService.deleteEmployee(deleteEmployee);
							System.out.println();
							System.out.println(String.valueOf(deleteEmployee.getEmployeeId()) + " Numaralı "
									+ deleteEmployee.getEmployeeName() + " " + deleteEmployee.getEmployeeSurname()
									+ " İşçisi İşten Çıkarılmıştır.");
							break;
						case 3:
							System.out.print("Eklemek İstediğiniz Ürünün Adını Giriniz: ");
							String newProductName = scanner.next();
							System.out.print("Eklemek İstediğiniz Ürünün Fiyatını Giriniz: ");
							double newProductPrice = scanner.nextDouble();
							productService.createProduct(newProductName, newProductPrice);
							break;
						case 4:
							System.out.print("Menuden Çıkarmak İstediğiniz Ürünün Numarasını Giriniz: ");
							int deleteProductId = scanner.nextInt();
							Product deleteProduct = productService.getProductById(deleteProductId);
							productService.deleteProduct(deleteProduct);
							System.out.println();
							System.out.println(String.valueOf(deleteProductId) + " Numaralı " + deleteProduct.getName()
									+ " Ürünü Kaldırılmıştır.");
							break;
						case 5:
							tableService.createTable();
							int newTableId = tableService.getAllTables().size();
							System.out.println("Masa " + String.valueOf(newTableId) + " Eklendi");
							hashMap.put(newTableId, null);
							break;
						case 6:
							System.out.print("Silmek İstediğiniz Masa Numarasını Giriniz: ");
							int deleteTableId = scanner.nextInt();
							Table deleteTable = tableService.getTableById(deleteTableId);
							tableService.deleteTable(deleteTable);
							System.out.println();
							System.out.println(String.valueOf(deleteTableId) + " Numaralı Masa Silinmiştir.");
							hashMap.remove(deleteTableId);
							break;
						case 7:
							System.out.print("Getirmek İstediğiniz Günün Tarihini Yıl-Ay-Gün Şeklinde Giriniz: ");
							String year_month_day = scanner.next();
							System.out.println();
							System.out.println(year_month_day.substring(0, 4) + "-" + year_month_day.substring(4, 6)
									+ "-" + year_month_day.substring(6) + " Tarihine Ait Siparişler...");
							double totalAmount = 0;
							List<Order> selectedOrders = orderService.getAllOrdersByDay(year_month_day);
							for (Order order : selectedOrders) {
								totalAmount = totalAmount + order.getTotalPrice();
								System.out.println(String.valueOf(order.getOrderId()) + " Numaralı Sipariş ---> "
										+ String.valueOf(order.getTotalPrice()) + " TL");
							}
							System.out.println("Toplam Ciro: " + String.valueOf(totalAmount) + " TL");
							break;
						case 8:
							System.out.println("İşletmenizde Çalışan Tüm İşçiler");
							List<Employee> allEmp = employeeService.getAllEmployees();
							for (Employee employee : allEmp) {
								System.out.println(String.valueOf(employee.getEmployeeId()) + " Numaralı "
										+ employee.getEmployeeName() + " " + employee.getEmployeeSurname());
							}
							break;
						case 9:
							System.out.println("İşletmenizdeki Tüm Ürünler");
							List<Product> allProduct = productService.getAllProducts();
							for (Product product : allProduct) {
								System.out.println(String.valueOf(product.getProductId()) + " <---> "
										+ product.getName() + " --> " + String.valueOf(product.getPrice()) + " TL");
							}
							break;
						case 10:
							System.out.println("Tüm Siparişler...");
							List<Order> allOrder = orderService.getAllOrders();
							for (Order order : allOrder) {
								System.out.println(String.valueOf(order.getOrderId()) + " --> "
										+ String.valueOf(order.getTotalPrice()) + " TL");
							}
							break;
						case 11:
							int reserveTableNumber = 0;
							int counterReserve = 0;
							do {
								if (counterReserve > 0) {
									System.out.println("Masa Boş Değil...");
								}
								System.out.print("Rezerve Etmek İstediğiniz Masa Numarasını Seçiniz: ");
								reserveTableNumber = scanner.nextInt();
								counterReserve = counterReserve + 1;
							} while (!emptyTable.contains(reserveTableNumber));
							Table updateTableReserve = tableService.getTableById(reserveTableNumber);
							tableService.reservationTable(updateTableReserve, true);
							reservedTableNumbers.add(reserveTableNumber);
							System.out.println(updateTableReserve.getTableName() + " Rezerve Edilmiştir.");
							break;
						case 12:
							int deleteReserveTableNumber = 0;
							int counter4 = 0;
							do {
								if (counter4 > 0) {
									System.out.println("Masa Rezerve Değil...");
								}
								System.out.print("Rezervasyon Silmek İstediğiniz Masa Numarasını Giriniz: ");
								deleteReserveTableNumber = scanner.nextInt();
								counter4 = counter4 + 1;
							} while (!reservedTableNumbers.contains(deleteReserveTableNumber));
							Table deleteTableReserve = tableService.getTableById(deleteReserveTableNumber);
							tableService.reservationTable(deleteTableReserve, false);
							int delIndex = reservedTableNumbers.indexOf(deleteReserveTableNumber);
							reservedTableNumbers.remove(delIndex);
							System.out.println(deleteTableReserve.getTableName() + " Rezervasyon Silinmiştir. ");
							break;
						case 13:
							System.out.println("Yönetici Panelinden Çıkılıyor...");
							exit = false;
							break;
						default:
							System.out.println("Geçersiz Seçim! Lütfen Geçerli Bir Seçim Yapın.");
							System.out.println();
							break;
						}
					}

					break;
				case 6:
					System.out.println("Programdan Çıkılıyor...");
					System.exit(0);
				default:
					System.out.println("Geçersiz Seçim! Lütfen Geçerli Bir Seçim Yapın.");
					System.out.println();
				}
			}
		}
	}
}
