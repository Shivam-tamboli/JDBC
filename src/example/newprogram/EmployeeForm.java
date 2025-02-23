package example.newprogram;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EmployeeForm extends Frame implements ActionListener {
    // UI Components
    Label l1, l2, l3, l4;
    TextField t1, t2, t3, t4;
    Button submit, fetch;
    TextArea display;

    // Database Credentials
    String url = "jdbc:mysql://localhost:3306/CompanyDB";
    String user = "root";
    String password = "1234";  // Change this to your actual MySQL password

    public EmployeeForm() {
        // Set Layout
        setLayout(new FlowLayout());

        // Labels
        l1 = new Label("Employee ID:");
        l2 = new Label("Name:");
        l3 = new Label("Designation:");
        l4 = new Label("Salary:");

        // TextFields
        t1 = new TextField(10);
        t2 = new TextField(20);
        t3 = new TextField(20);
        t4 = new TextField(10);

        // Buttons
        submit = new Button("Submit");
        fetch = new Button("Fetch Data");

        // Text Area to Display Data
        display = new TextArea(10, 50);
        display.setEditable(false); // Make text area read-only

        // Add Components
        add(l1); add(t1);
        add(l2); add(t2);
        add(l3); add(t3);
        add(l4); add(t4);
        add(submit);
        add(fetch);
        add(display);

        // Register Action Listeners
        submit.addActionListener(this);
        fetch.addActionListener(this);

        // Set Frame Properties
        setSize(450, 500);
        setTitle("Employee Form");
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            insertEmployee();
        } else if (e.getSource() == fetch) {
            fetchEmployees();
        }
    }

    // Method to Insert Employee Data into Database
    public void insertEmployee() {
        String query = "INSERT INTO employee (employee_id, employee_name, designation, salary) VALUES (101, 'Michael Scott', 'Regional Manager', 75000.50)";


        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, Integer.parseInt(t1.getText())); // Employee ID
            pstmt.setString(2, t2.getText()); // Name
            pstmt.setString(3, t3.getText()); // Designation
            pstmt.setDouble(4, Double.parseDouble(t4.getText())); // Salary

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                display.setText("✅ Employee Added Successfully!\n");
                clearFields(); // Clear input fields after successful insertion
            }

        } catch (Exception ex) {
            display.setText("❌ Error: " + ex.getMessage());
        }
    }

    // Method to Fetch Employee Data from Database
    public void fetchEmployees() {
        String query = "SELECT * FROM employee";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            StringBuilder data = new StringBuilder();
            data.append("Employee ID | Name       | Designation       | Salary\n");
            data.append("----------------------------------------------------\n");

            while (rs.next()) {
                data.append(String.format("%11d | %-10s | %-15s | %.2f\n",
                        rs.getInt("employee_id"),
                        rs.getString("employee_name"),
                        rs.getString("designation"),
                        rs.getDouble("salary")));
            }

            display.setText(data.toString());

        } catch (Exception ex) {
            display.setText("❌ Error: " + ex.getMessage());
        }
    }

    // Method to Clear Input Fields
    private void clearFields() {
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
    }

    public static void main(String[] args) {
        new EmployeeForm();
    }
}
