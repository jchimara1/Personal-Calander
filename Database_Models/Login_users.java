package Database_Models;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *  creates the Login_user database model
 */
public class Login_users {
    private int id;
    private String userName;

    public Login_users() {
    }

    /**
     *  @param id for the id
     *  @param userName for  username
     */
    public Login_users(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    /**
     * @return UserID getter
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @param resultSet returns a resultset for users in the Database
     * @throws SQLException if error occurs
     */
    public void populateUser(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt(1);
        userName = resultSet.getString(2);
    }

    /**
     * displays the User in the console
     */
    @Override
    public String toString() {
        return "User{id=" + id +", userName='" + userName + '\'' + '}';
    }
}
