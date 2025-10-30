package org.employeemanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // URL for database connection
        // Replace with actual URL
        final String url = "jdbc:mysql://localhost:3306/employeeDB";

        // Database username
        // Replace with actual username
        final String user = "your_username";

        // Database password
        // Replace with actual password
        final String password = "your_password";

        // Try-with-resources automatically closes the connection
        try (Connection con = DriverManager.getConnection(url, user, password)) {

            /*  Employee Management System
                Creating a new connection for each request is expensive,
                so pass the connection object through the constructor.
             */
            EmployeeManagement employeeManagement = new EmployeeManagement(con);


            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n===================================================");
                System.out.println("1. Add Employee");
                System.out.println("2. Get Employee by ID");
                System.out.println("3. Get All Employees");
                System.out.println("4. Update Employee");
                System.out.println("5. Delete Employee");
                System.out.println("6. Exit");
                System.out.println("===================================================\n");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                System.out.println();

                switch (choice) {

                    // --- Add New Employee ---
                    case 1: {
                        System.out.print("Enter First Name: ");
                        String firstName = sc.next();
                        System.out.print("\nEnter Last Name: ");
                        String lastName = sc.next();
                        System.out.print("\nEnter Department: ");
                        String department = sc.next();
                        System.out.print("\nEnter Salary: ");
                        double salary = sc.nextDouble();

                        Employee employee = new Employee();
                        employee.setFirstName(firstName);
                        employee.setLastName(lastName);
                        employee.setDepartment(department);
                        employee.setSalary(salary);

                        boolean success = employeeManagement.addEmployee(employee);

                        if (success) {
                            System.out.println("Employee added successfully");
                        } else {
                            System.out.println("Failed to add employee");
                        }

                        break;
                    }

                    // --- Get Employee by ID ---
                    case 2: {
                        System.out.print("Enter Employee ID: ");
                        int id = sc.nextInt();
                        System.out.println();

                        Employee employee = employeeManagement.getEmployeeById(id);

                        if (employee == null) {
                            System.out.println("Employee with ID " + id + " not found");
                        } else {
                            printEmployee(employee);
                        }

                        break;
                    }

                    // --- Get All Employees ---
                    case 3: {
                        List<Employee> employees = employeeManagement.getAllEmployees();

                        if (employees.isEmpty()) {
                            System.out.println("No employees found");
                        } else {
                            printEmployees(employees);
                        }
                        break;
                    }

                    // --- Update Employee by ID ---
                    case 4: {
                        System.out.print("Enter Employee ID: ");
                        int id = sc.nextInt();

                        Employee employee = new Employee();

                        System.out.print("\nDo you want to update First Name? (Y/N): ");
                        String option = sc.next();
                        if (option.equalsIgnoreCase("Y")) {
                            System.out.print("\nEnter First Name: ");
                            String firstName = sc.next();
                            employee.setFirstName(firstName);
                        }

                        System.out.print("\nDo you want to update Last Name? (Y/N): ");
                        option = sc.next();
                        if (option.equalsIgnoreCase("Y")) {
                            System.out.print("\nEnter Last Name: ");
                            String lastName = sc.next();
                            employee.setLastName(lastName);
                        }

                        System.out.print("\nDo you want to update Department? (Y/N): ");
                        option = sc.next();
                        if (option.equalsIgnoreCase("Y")) {
                            System.out.print("\nEnter Department: ");
                            String department = sc.next();
                            employee.setDepartment(department);
                        }

                        System.out.print("\nDo you want to update Salary? (Y/N): ");
                        option = sc.next();
                        if (option.equalsIgnoreCase("Y")) {
                            System.out.print("\nEnter Salary: ");
                            double salary = sc.nextDouble();
                            employee.setSalary(salary);
                        }

                        /*  If no field is updated (i.e., entered 'N' for all options),
                            there is no need to perform an update. It’s an invalid operation.
                         */
                        if (employee.getFirstName() == null
                                && employee.getLastName() == null
                                && employee.getDepartment() == null
                                && employee.getSalary() == 0) {
                            System.out.println("Invalid operation. Please update at least one field.");
                        } else {

                            boolean success = employeeManagement.updateEmployee(id, employee);

                            if (success) {
                                System.out.println("Employee updated successfully");
                            } else {
                                System.out.println("Failed to update employee");
                            }
                        }
                        break;
                    }

                    // --- Delete Employee by ID ---
                    case 5: {
                        System.out.print("Enter Employee ID: ");
                        int id = sc.nextInt();
                        System.out.println();

                        boolean success = employeeManagement.deleteEmployee(id);
                        if (success) {
                            System.out.println("Employee deleted successfully");
                        } else {
                            System.out.println("Failed to delete employee");
                            System.out.println("Employee with ID " + id + " not found");
                        }
                        break;
                    }

                    // --- Exit ---
                    case 6: {
                        return;
                    }

                    // --- Invalid Input ---
                    default: {
                        System.out.println("Invalid choice");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Prints all employees with column details.
     *
     * @param employees List of employees to print
     */
    private static void printEmployees(List<Employee> employees) {
        System.out.printf("%-10s %-15s %-15s %-15s %-10s%n", "emp_ID", "first_name", "last_name", "department", "salary");
        System.out.println("------------------------------------------------------------------");
        for (Employee employee : employees) {
            System.out.printf("%-10d %-15s %-15s %-15s %-10.2f%n",
                    employee.getId(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getDepartment(),
                    employee.getSalary()
            );
        }
    }

    /**
     * Prints details of a single employee.
     *
     * @param employee Employee to print
     */
    private static void printEmployee(Employee employee) {
        // Pass employee as a list, and the method handles formatting
        printEmployees(List.of(employee));
    }
}
