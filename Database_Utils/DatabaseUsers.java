package Database_Utils;

import util.DatabaseConnection;
import Database_Models.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Method to Query Database Users
 */
public class DatabaseUsers {


    /**
     *
     * @return All Users from Database that can be used later in the project
     * @throws SQLException if error occurs
     */
    public static ObservableList<Users> getDBUsers() throws SQLException {
        ObservableList<Users> DBUsers = FXCollections.observableArrayList();
        String query = "SELECT * from users";
        PreparedStatement prestatement = DatabaseConnection.getConnection().prepareStatement(query);
        ResultSet results = prestatement.executeQuery();
        while (results.next()) {
            int id = results.getInt("User_ID");
            String User_Name = results.getString("User_Name");
            String User_Password = results.getString("Password");
            Users user = new Users(id, User_Name, User_Password);
            DBUsers.add(user);
        }
        return DBUsers;
    }
}
