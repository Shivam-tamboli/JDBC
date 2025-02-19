package example.newprogram;

import java.sql.*;

public class StudentReteriveInformation {  // Fixed Typo in Class Name
    private static final String url = "jdbc:mysql://localhost:3306/mybd";
    private static final String user = "root";
    private static final String password = "1234";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        }

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            // Corrected query with proper format specifiers
            String query = String.format("INSERT INTO Student(name, age, marks) VALUES('%s', %d, %.2f)",
                    "Satyam", 23, 89.9);

            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println("Student Data has been inserted successfully.");
            } else {
                System.out.println("Student data insertion failed.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
