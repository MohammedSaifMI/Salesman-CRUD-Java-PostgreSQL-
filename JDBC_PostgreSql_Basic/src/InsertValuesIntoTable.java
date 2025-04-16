import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertValuesIntoTable {
    public static void insert(Connection conn, Scanner sc) {
        String query = "INSERT INTO SALESMAN (ID, NAME, Commission, City) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            String choice;
            do {
                int id = -1;
                while (id <= 0) {
                    try {
                        System.out.print("Enter a valid ID: ");
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
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        sc.nextLine();
                    }
                }

                String name = "";
                while (name.isEmpty()) {
                    try {
                        System.out.print("Enter Name: ");
                        name = sc.nextLine().trim();
                        if (name.isEmpty()) {
                            System.out.println("Name cannot be empty. Please try again.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                double commission = -1;
                while (commission < 0) {
                    try {
                        System.out.print("Enter Commission (positive number): ");
                        if (sc.hasNextDouble()) {
                            commission = sc.nextDouble();
                            sc.nextLine();
                            if (commission < 0) {
                                System.out.println("Commission must be a positive number.");
                            }
                        } else {
                            System.out.println("Please enter a valid number for Commission.");
                            sc.nextLine();
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        sc.nextLine();
                    }
                }

                String city = "";
                while (city.isEmpty() || !city.matches("[a-zA-Z ]+")) {
                    try {
                        System.out.print("Enter City: ");
                        city = sc.nextLine().trim();
                        if (city.isEmpty()) {
                            System.out.println("City cannot be empty. Please try again.");
                        } else if (!city.matches("[a-zA-Z ]+")) {
                            System.out.println("City should only contain letters and spaces.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                stmt.setInt(1, id);
                stmt.setString(2, name);
                stmt.setDouble(3, commission);
                stmt.setString(4, city);

                try {
                    int rowsAffected = stmt.executeUpdate();
                    System.out.println("Row inserted. Rows affected: " + rowsAffected);
                } catch (SQLException e) {
                    System.err.println("SQL Error during insert: " + e.getMessage());
                }

                System.out.print("Type 'Exit' to stop or press Enter to insert another: ");
                choice = sc.nextLine();

            } while (!choice.equalsIgnoreCase("Exit"));

            System.out.println("Insert operation finished. Thank you!");
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}
