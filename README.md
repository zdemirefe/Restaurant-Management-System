# Restaurant Management System

## Project Overview
This project is a simple Restaurant Management System that uses text files (`table.txt`, `order.txt`, `product.txt`, `employee.txt`) as its database to manage restaurant operations.  
The system supports essential functionalities such as order taking, payment processing, employee management, and table management.

## Features
- **Order Management:**  
  Take and track customer orders, update order status.
  
- **Payment Processing:**  
  Handle payments for orders with basic transaction recording.
  
- **Employee Management:**  
  Add, update, and manage restaurant employees.
  
- **Table Management:**  
  Add new tables, assign orders to tables, track table status (occupied/free).

## Data Storage
- The system uses plain text files as a lightweight database:  
  - `table.txt` — stores information about tables  
  - `order.txt` — stores order details  
  - `product.txt` — stores menu/product information  
  - `employee.txt` — stores employee records

## Usage
- Add employees to the system through the employee management module.  
- Add and manage tables for seating customers.  
- Take orders linked to tables and products.  
- Process payments and update order statuses accordingly.

## Requirements
- Python 3.x (or the language/environment used)  
- Basic file system access to read/write text files  

## How to Run
1. Make sure the `.txt` files (`table.txt`, `order.txt`, `product.txt`, `employee.txt`) are in the project directory.  
2. Run the main application script.  
3. Use the menu or commands to interact with the system functionalities (e.g., add employee, take order, process payment).

## Notes
- This system uses a simple file-based approach and is suitable for small-scale or educational purposes.  
- For production environments, consider migrating to a proper database system for scalability and reliability.
- To run it on your own computer, you need to adjust your txt file paths according to your own computer. !!!!!!  
