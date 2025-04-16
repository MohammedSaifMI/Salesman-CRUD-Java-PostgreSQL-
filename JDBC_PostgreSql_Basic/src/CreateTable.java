import java.sql.Connection;
import java.sql.Statement;

public class CreateTable {
    public static void create(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS SALESMAN (" +
                "ID INT PRIMARY KEY NOT NULL, " +
                "NAME TEXT NOT NULL, " +
                "Commission INT NOT NULL, " +
                "City CHAR(30) NOT NULL)";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Table created successfully (if it didn't already exist).");
        } catch (Exception e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }
}
