package Database_Utils;

import util.DatabaseConnection;
import Database_Models.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Method to Query Database Contacts
 */
public class DatabaseContacts {
    /**
     *
     * @return all contacts from the database
     * @throws SQLException if error occurs
     */
    public static ObservableList<Contacts> getDBContacts() throws SQLException {
        ObservableList<Contacts> DBContacts = FXCollections.observableArrayList();
        String sql = "SELECT * from contacts";
        PreparedStatement prestatement = DatabaseConnection.getConnection().prepareStatement(sql);
        ResultSet results = prestatement.executeQuery();
        while (results.next()) {
            int id = results.getInt("Contact_ID");
            String name = results.getString("Contact_Name");
            String email = results.getString("Email");
            Contacts contact = new Contacts(id, name, email);
            DBContacts.add(contact);
        }
        return DBContacts;
    }
}
