package Database_Models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates the regular Users Database model
 */
public class Users {
    public int id;
    public String userName;
    public String userPassword;

    /**
     *
     * @param  id required
     * @param userName required
     * @param  userPassword required
     */
    public Users(int id, String userName, String userPassword) {
        this.id = this.id;
        this.userName = this.userName;
        this.userPassword = this.userPassword;
    }

    public Users() {

    }

    /**
     *
     * @return the user ID
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @return the user password
     */
    public String getUserPassword() {
        return userPassword;
    }

    public void populateUser(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt(1);
        userName = resultSet.getString(2);
        userPassword = resultSet.getString(3);
    }
    @Override
    public String toString() {
        return "User{id=" + id +", userName='" + userName + '\'' + '}';
    }
}