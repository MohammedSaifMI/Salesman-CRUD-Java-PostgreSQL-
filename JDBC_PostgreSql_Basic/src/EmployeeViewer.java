import java.sql.*;

public class EmployeeViewer {
    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "1234";

        String query = "SELECT * FROM Employee where employeeid=3";

        try (
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
        ) {
            System.out.println("Employee Records:");
            System.out.println("---------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("EmployeeID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");
                Date hireDate = rs.getDate("HireDate");
                String jobTitle = rs.getString("JobTitle");
                double salary = rs.getDouble("Salary");
                String department = rs.getString("Department");

                System.out.printf("ID: %d | Name: %s %s | Email: %s | Phone: %s%n", id, firstName, lastName, email, phone);
                System.out.printf("Hired: %s | Job: %s | Salary: %.2f | Dept: %s%n", hireDate, jobTitle, salary, department);
                System.out.println("---------------------------------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to the database.");
            System.out.println("Details: " + e.getMessage());
        }
    }
}
