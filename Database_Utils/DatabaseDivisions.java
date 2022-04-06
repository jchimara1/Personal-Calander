package Database_Utils;

import util.DatabaseConnection;
import Database_Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Method to Query Database Divisions
 */
public class DatabaseDivisions {

    /**
     *
     * @return all Divisions from the Database
     * @throws SQLException if error occurs
     */
    public static ObservableList<Division> getDBDivisions() throws SQLException {
        ObservableList<Division> DBDivisions = FXCollections.observableArrayList();
        String query = "SELECT * from first_level_divisions";
        PreparedStatement prestatement = DatabaseConnection.getConnection().prepareStatement(query);
        ResultSet results = prestatement.executeQuery();

        while (results.next()) {
            int dID = results.getInt("Division_ID");
            String name = results.getString("Division");
            int country_id = results.getInt("COUNTRY_ID");
            Division Division = new Division(dID, name, country_id);
            DBDivisions.add(Division);
        }
        return DBDivisions;
    }
}
