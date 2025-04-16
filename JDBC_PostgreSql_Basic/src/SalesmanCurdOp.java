import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class SalesmanCurdOp {
    static String url = "jdbc:postgresql://localhost:5432/postgres";
    static String user = "postgres";
    static String password = "1234";

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                System.out.println("Connected to database...");
                int userChoice = -1;

                do {
                    System.out.println("\n\t-------Welcome to Salesman Curd Op-------");
                    System.out.println("1. Insert Values");
                    System.out.println("2. Update Values");
                    System.out.println("3. Delete Values");
                    System.out.println("4. Show All Values");
                    System.out.println("5. Exit");
                    System.out.print("Enter your choice: ");

                    try {
                        userChoice = Integer.parseInt(sc.nextLine());
                        switch (userChoice) {
                            case 1:
                                InsertValuesIntoTable.insert(conn, sc);
                                break;
                            case 2:
                                UpdateOperationTable.update(conn, sc);
                                break;
                            case 3:
                                DeleteValueTable.delete(conn);
                                break;
                            case 4:
                                ShowAllData.show(conn);
                                break;
                            case 5:
                                System.out.println("Exiting... Goodbye!");
                                break;
                            default:
                                System.out.println("Invalid choice, please try again.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Please enter a valid number.");
                    }

                } while (userChoice != 5);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
