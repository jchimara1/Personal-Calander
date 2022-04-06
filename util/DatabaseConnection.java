package util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Class to create a database connection
 */
public class DatabaseConnection {

    private static Connection connection;

    /**
     * method to create a database connection
     */
    public static void openConnection() {
        try {
            Class.forName(Constant.JDBC_DRIVER); // Locate Driver
            connection = DriverManager.getConnection(Constant.JDBC_CONNECTION_URL, Constant.USERNAME, Constant.PASSWORD); // Reference Connection object
            System.out.println("Check Point : Connection successful!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error:" + e.getMessage());
        }

    }

    /**
     * method to close a database connection
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Checkpoint : Connection closed!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error:" + e.getMessage());
        }

    }

    /**
     *
     * @return connection
     */
    public static Connection getConnection() {
        return connection;
    }
}
