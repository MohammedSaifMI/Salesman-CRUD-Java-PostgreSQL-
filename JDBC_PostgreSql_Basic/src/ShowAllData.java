import java.sql.*;

public class ShowAllData {
    public static void show(Connection conn) {
        String query = "SELECT * FROM SALESMAN";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n----- All Salesman Records -----");
            System.out.printf("%-10s %-20s %-15s %-20s%n", "ID", "Name", "Commission", "City");
            System.out.println("---------------------------------------------------------------");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                int commission = rs.getInt("COMMISSION");
                String city = rs.getString("CITY");

                System.out.printf("%-10d %-20s %-15d %-20s%n", id, name, commission, city);
            }

            if (!hasData) {
                System.out.println("No data found in the SALESMAN table.");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving data: " + e.getMessage());
        }
    }
}
