# 🧾 Employee Management System (JDBC + Maven)

This is a simple **Employee Management System** built using **Java JDBC**, **MySQL**, and **Maven**.  
It demonstrates how to connect Java applications with a database and perform basic CRUD (Create, Read, Update, Delete)
operations.

---

## Features

- **Add Employee** – Insert new employee records into the database
- **Get Employee by ID** – Retrieve specific employee details
- **Get All Employees** – Fetch and display all employees
- **Update Employee** – Modify selected employee fields
- **Delete Employee** – Remove employees by ID

---

## Requirements

- **Java 17+**
- **Maven** – Build and dependency management
- **MySQL** – Database for storing employee details
- **JDBC** – To connect Java with MySQL

---

## How to Run the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/pavanpai2004/Employee-Management-System.git
   cd Employee-Management-System

2. Update database configuration:
   Open your Main.java file and replace the following with your actual MySQL details:
    ```java 
    final String url = "jdbc:mysql://localhost:3306/employeeDB";
    final String user = "your_username";
    final String password = "your_password";

3. Run the project using Maven:
    ```bash
   mvn clean compile exec:java -Dexec.mainClass="org.employeemanagement.Main"

---

## Key Concepts Learned

- Using Maven for project setup and dependency management

- Understanding JDBC Core Classes:
  `Connection`, `DriverManager`, `Statement`, `PreparedStatement`, `ResultSet`

- Establishing and closing database connections safely

- Handling SQL Exceptions effectively

- Implementing try-with-resources for automatic resource cleanup

- Writing SQL queries inside Java code for CRUD operations

---

## Credits

- JDBC tutorial by Learn Code With
  Durgesh - [YouTube Link](https://www.youtube.com/watch?v=6EF8XPJp-No&list=PL0zysOflRCenjuvOwumYLG9TCsEQZrV2M&index=1)
- ChatGPT: [https://chat.openai.com](https://chat.openai.com)