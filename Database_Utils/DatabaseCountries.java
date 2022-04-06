package Database_Utils;

import util.DatabaseConnection;
import Database_Models.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Method to Query Database Countries
 */
public class DatabaseCountries {

    /**
     *
     * @return all Countries from the Database
     * @throws SQLException if error occurs
     */
    public static ObservableList<Country> getDBcountries() throws SQLException {
        ObservableList<Country> DBcountries = FXCollections.observableArrayList();
        String sql = "SELECT * from countries";
        PreparedStatement prestatement = DatabaseConnection.getConnection().prepareStatement(sql);
        ResultSet results = prestatement.executeQuery();
        while (results.next()) {
            int id = results.getInt("Country_ID");
            String name = results.getString("Country");
            Country country = new Country(id, name);
            DBcountries.add(country);
        }
        return DBcountries;
    }
}
