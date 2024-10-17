import java.sql.*;
import java.util.Scanner;

public class AirlineManagementSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nAirline Management System");
            System.out.println("1. Add Customer");
            System.out.println("2. Add Flight");
            System.out.println("3. View Customers");
            System.out.println("4. View Flights");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addCustomer(scanner);
                    break;
                case 2:
                    addFlight(scanner);
                    break;
                case 3:
                    viewCustomers();
                    break;
                case 4:
                    viewFlights();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        scanner.close();
    }

    private static void addCustomer(Scanner scanner) {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();
        System.out.print("Enter customer phone: ");
        String phone = scanner.nextLine();

        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO Customers (name, email, phone) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.executeUpdate();
            System.out.println("Customer added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addFlight(Scanner scanner) {
        System.out.print("Enter flight number: ");
        String flightNumber = scanner.nextLine();
        System.out.print("Enter origin: ");
        String origin = scanner.nextLine();
        System.out.print("Enter destination: ");
        String destination = scanner.nextLine();
        System.out.print("Enter departure time (YYYY-MM-DD HH:MM:SS): ");
        String departureTime = scanner.nextLine();

        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO Flights (flight_number, origin, destination, departure_time) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, flightNumber);
            pstmt.setString(2, origin);
            pstmt.setString(3, destination);
            pstmt.setString(4, departureTime);
            pstmt.executeUpdate();
            System.out.println("Flight added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewCustomers() {
        try (Connection connection = DBConnection.getConnection();
             Statement stmt = connection.createStatement()) {
            String query = "SELECT * FROM Customers";
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("\nCustomers:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("customer_id") + 
                                   ", Name: " + rs.getString("name") + 
                                   ", Email: " + rs.getString("email") + 
                                   ", Phone: " + rs.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewFlights() {
        try (Connection connection = DBConnection.getConnection();
             Statement stmt = connection.createStatement()) {
            String query = "SELECT * FROM Flights";
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("\nFlights:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("flight_id") + 
                                   ", Flight Number: " + rs.getString("flight_number") + 
                                   ", Origin: " + rs.getString("origin") + 
                                   ", Destination: " + rs.getString("destination") + 
                                   ", Departure Time: " + rs.getString("departure_time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
