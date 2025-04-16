import java.sql.*;
import java.util.Scanner;

public class InsertOperationDb {

    static String url = "jdbc:postgresql://localhost:5432/postgres";
    static String user = "postgres";
    static String password = "1234";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                System.out.println("Connected to PostgreSQL database");

                int choice;
                do {
                    System.out.println("\n******** Employee Management ********");
                    System.out.println("1. Insert Employee");
                    System.out.println("2. Read the list of Employee Records");
                    System.out.println("3. Exit Program");
                    System.out.print("Enter your choice: ");
                    choice = sc.nextInt();
                    sc.nextLine(); // consume newline

                    switch (choice) {
                        case 1:
                            insertEmployee(conn, sc);
                            break;
                        case 2:
                            readEmployees(conn);
                            break;
                        case 3:
                            System.out.println("Exiting Program...");
                            break;
                        default:
                            System.out.println("Invalid choice. Try again.");
                    }
                } while (choice != 3);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void insertEmployee(Connection conn, Scanner sc) {
        String query = "INSERT INTO Employee (EmployeeID, FirstName, LastName, Email, Phone, HireDate, JobTitle, Salary, Department) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            System.out.print("Enter Employee ID: ");
            ps.setInt(1, sc.nextInt());
            sc.nextLine();

            System.out.print("Enter First Name: ");
            ps.setString(2, sc.nextLine());

            System.out.print("Enter Last Name: ");
            ps.setString(3, sc.nextLine());

            System.out.print("Enter Email: ");
            ps.setString(4, sc.nextLine());

            System.out.print("Enter Phone Number: ");
            ps.setString(5, sc.nextLine());

            System.out.print("Enter Hire Date (YYYY-MM-DD): ");
            ps.setDate(6, Date.valueOf(sc.nextLine()));

            System.out.print("Enter Job Title: ");
            ps.setString(7, sc.nextLine());

            System.out.print("Enter Salary: ");
            ps.setDouble(8, sc.nextDouble());
            sc.nextLine();

            System.out.print("Enter Department: ");
            ps.setString(9, sc.nextLine());

            int rows = ps.executeUpdate();
            System.out.println(rows + " Employee Record(s) Inserted Successfully");

        } catch (SQLException e) {
            System.out.println("Insertion Error: " + e.getMessage());
        }
    }

    static void readEmployees(Connection conn) {
        String sql = "SELECT * FROM Employee";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Employee List ---");
            while (rs.next()) {
                System.out.printf("ID: %d | %s %s | %s | %s | %s | %s | %.2f | %s%n",
                        rs.getInt("EmployeeID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getDate("HireDate"),
                        rs.getString("JobTitle"),
                        rs.getDouble("Salary"),
                        rs.getString("Department")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error reading records: " + e.getMessage());
        }
    }
}
