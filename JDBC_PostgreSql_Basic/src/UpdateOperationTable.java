import java.sql.*;
import java.util.Scanner;

public class UpdateOperationTable {
    public static void update(Connection conn, Scanner sc) throws SQLException {
        System.out.println("Connected to database......");

        int id = -1;
        while (id <= 0) {
            System.out.print("Enter the ID of the operation you would like to update: ");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                sc.nextLine();
                if (id <= 0) {
                    System.out.println("ID must be a positive integer.");
                }
            } else {
                System.out.println("Please enter a valid number for ID.");
                sc.nextLine();
            }
        }

        String fetchSql = "SELECT * FROM SALESMAN WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(fetchSql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("No record found for ID: " + id);
                return;
            }

            System.out.println("\nCurrent Data:");
            System.out.println("ID: " + rs.getInt("ID"));
            System.out.println("Name: " + rs.getString("NAME"));
            System.out.println("Commission: " + rs.getInt("COMMISSION"));
            System.out.println("City: " + rs.getString("CITY"));
        }

        String newName = null;
        Integer newCommission = null;
        String newCity = null;

        System.out.print("Update Name? (Y/N): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.print("Enter new Name: ");
            newName = sc.nextLine().trim();
            while (newName.isEmpty()) {
                System.out.print("Name cannot be empty. Enter new Name: ");
                newName = sc.nextLine().trim();
            }
        }

        System.out.print("Update Commission? (Y/N): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            newCommission = -1;
            while (newCommission < 0) {
                System.out.print("Enter new Commission: ");
                if (sc.hasNextInt()) {
                    newCommission = sc.nextInt();
                    sc.nextLine();
                    if (newCommission < 0) {
                        System.out.println("Commission must be a positive number.");
                    }
                } else {
                    System.out.println("Please enter a valid number for Commission.");
                    sc.nextLine();
                }
            }
        }

        System.out.print("Update City? (Y/N): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.print("Enter new City: ");
            newCity = sc.nextLine().trim();
            while (newCity.isEmpty()) {
                System.out.print("City cannot be empty. Enter new City: ");
                newCity = sc.nextLine().trim();
            }
        }

        if (newName == null && newCommission == null && newCity == null) {
            System.out.println("No changes requested.");
            return;
        }

        StringBuilder sql = new StringBuilder("UPDATE SALESMAN SET ");
        if (newName != null) sql.append("NAME = ?, ");
        if (newCommission != null) sql.append("COMMISSION = ?, ");
        if (newCity != null) sql.append("CITY = ?, ");
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE id = ?");

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (newName != null) ps.setString(paramIndex++, newName);
            if (newCommission != null) ps.setInt(paramIndex++, newCommission);
            if (newCity != null) ps.setString(paramIndex++, newCity);
            ps.setInt(paramIndex, id);

            int rows = ps.executeUpdate();
            System.out.println("\nUpdate complete. Rows affected: " + rows);
        }

        try (PreparedStatement fetchStmt = conn.prepareStatement(fetchSql)) {
            fetchStmt.setInt(1, id);
            ResultSet rs = fetchStmt.executeQuery();
            if (rs.next()) {
                System.out.println("\nUpdated Data:");
                System.out.println("ID: " + rs.getInt("ID"));
                System.out.println("Name: " + rs.getString("NAME"));
                System.out.println("Commission: " + rs.getInt("COMMISSION"));
                System.out.println("City: " + rs.getString("CITY"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
