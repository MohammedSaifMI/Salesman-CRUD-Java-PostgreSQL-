import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DeleteValueTable {
    static Scanner sc = new Scanner(System.in);

    public static void delete(Connection conn) {
        int id = -1;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter the ID to be deleted from the table: ");
                id = sc.nextInt();
                sc.nextLine();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. ID must be an integer. Please try again.");
                sc.nextLine();
            }
        }

        try {
            String fetchSql = "SELECT * FROM SALESMAN WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(fetchSql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    System.out.println("Salesman ID does not exist.");
                    return;
                }

                System.out.println("Salesman ID: " + rs.getInt("ID"));
                System.out.println("Name: " + rs.getString("NAME"));
                System.out.println("Commission: " + rs.getDouble("COMMISSION"));
                System.out.println("City: " + rs.getString("CITY"));
            }

            System.out.print("Are you sure you want to delete this record? (Y/N): ");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                String deleteSql = "DELETE FROM SALESMAN WHERE id = ?";
                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                    deleteStmt.setInt(1, id);
                    int rowsAffected = deleteStmt.executeUpdate();
                    System.out.println("Deleted " + rowsAffected + " row(s).");
                }
            } else {
                System.out.println("Deletion process cancelled.");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Constraint violation: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
    }
}
