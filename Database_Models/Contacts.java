package Database_Models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates the Contacts Database Model
 */
public class Contacts {
    public int id;
    public String name;
    public String email;

    /**
     *
     * @param id the contact ID to set
     * @param name the contact name to set
     * @param email the contact email to set
     */
    public Contacts(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     *
     * @return the contact ID
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return the contact name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the contact email
     */
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return name;
    }

    public void populateAppointment(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt(1);
        name = resultSet.getString(2);
        email = resultSet.getString(3);
    }
}
