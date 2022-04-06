package Database_Utils;

import util.DatabaseConnection;
import Database_Models.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Method to Query Database Customers
 */
public class DatabaseCustomers {
    /**
     *
     * @return all Customers from the Database
     * @throws SQLException if exception has occurred
     */
    public static ObservableList<Customers> getDBcustomers() throws SQLException {
        ObservableList<Customers> DBCustomers = FXCollections.observableArrayList();
        String sql = "SELECT * from customers";
        PreparedStatement prestatement = DatabaseConnection.getConnection().prepareStatement(sql);
        ResultSet results = prestatement.executeQuery();
        while (results.next()) {
            int customerID = results.getInt("Customer_ID");
            String name = results.getString("Customer_Name");
            String address = results.getString("Address");
            String zipcode = results.getString("Postal_Code");
            String phone = results.getString("Phone");
            int dID = results.getInt("Division_ID");
            Customers customer = new Customers(customerID, name, address, zipcode, phone, dID);
            DBCustomers.add(customer);
        }
        return DBCustomers;
    }
}
