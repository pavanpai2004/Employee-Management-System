package org.employeemanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManagement {

    // Connection object
    private Connection connection;

    public EmployeeManagement(Connection connection) {
        this.connection = connection;

        // Ensures the table is created when the object is instantiated
        createTable();
    }

    /**
     * Creates the employee table if it does not already exist in the database.
     */
    private void createTable() {

        String sql = """
                
                CREATE TABLE IF NOT EXISTS employee (
                    emp_Id INT PRIMARY KEY AUTO_INCREMENT,
                    first_name VARCHAR(15) NOT NULL,
                    last_name VARCHAR(15),
                    department VARCHAR(15) NOT NULL,
                    salary DOUBLE NOT NULL
                )
                """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new record in the database (i.e., adds the employee as a row).
     *
     * @param employee Employee object containing details to be added
     * @return true if the operation is successful, false otherwise
     */
    public boolean addEmployee(Employee employee) {
        String sql = """
                        INSERT INTO employee
                        (first_name, last_name, department, salary)
                        VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getDepartment());
            stmt.setDouble(4, employee.getSalary());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves all employees stored in the database.
     *
     * @return List of employees if records exist,
     * an empty list if the table does not contain any records
     */
    public List<Employee> getAllEmployees() {
        String sql = """
                        SELECT * FROM employee
                """;

        List<Employee> employees = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                // Create a new employee object for each row
                Employee employee = new Employee();

                employee.setId(rs.getInt("emp_Id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setDepartment(rs.getString("department"));
                employee.setSalary(rs.getDouble("salary"));

                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    /**
     * Retrieves an employee by the given ID.
     *
     * @param id Employee ID
     * @return Employee object if found, null otherwise
     */
    public Employee getEmployeeById(int id) {
        String sql = """
                        SELECT * FROM employee WHERE emp_Id = ?;
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("emp_Id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setDepartment(rs.getString("department"));
                employee.setSalary(rs.getDouble("salary"));

                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes an employee with the given ID from the table.
     *
     * @param id Employee ID
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteEmployee(int id) {
        String sql = """
                        DELETE FROM employee WHERE emp_Id = ?;
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int row = stmt.executeUpdate();
            if (row > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an existing employee with new details.
     * This method takes an Employee object with updated fields,
     * retrieves the existing employee from the table,
     * and updates only the necessary fields while keeping others unchanged.
     *
     * @param id       Employee ID
     * @param employee Employee object with updated fields
     * @return true if successful, false otherwise
     */
    public boolean updateEmployee(int id, Employee employee) {
        String sql = """
                    UPDATE employee
                     SET  first_name = ?,
                          last_name = ?,
                          department = ?,
                          salary = ?
                    WHERE emp_Id = ?;
                """;

        // Get the existing record from the table
        Employee employeeInDB = getEmployeeById(id);

        // Employee with the given ID not found
        if (employeeInDB == null) return false;

        // Update only the necessary fields
        if (employee.getFirstName() == null) {
            employee.setFirstName(employeeInDB.getFirstName());
        }

        if (employee.getLastName() == null) {
            employee.setLastName(employeeInDB.getLastName());
        }

        if (employee.getDepartment() == null) {
            employee.setDepartment(employeeInDB.getDepartment());
        }

        if (employee.getSalary() == 0) {
            employee.setSalary(employeeInDB.getSalary());
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getDepartment());
            stmt.setDouble(4, employee.getSalary());
            stmt.setInt(5, id);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
