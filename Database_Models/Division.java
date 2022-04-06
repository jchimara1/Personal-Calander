package Database_Models;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *  creates the Division database model
 */
public class Division {
    private int id;
    private String divisionName;
    public int country_ID;

    /**
     *
     * @param id the division ID to set
     * @param divisionName the division name to set
     * @param country_ID the country ID to set
     */
    public Division(int id, String divisionName, int country_ID) {
        this.id = id;
        this.divisionName = divisionName;
        this.country_ID = country_ID;
    }

    /**
     *
     * @return the division ID
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return the division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     *
     * @return the country ID
     */
    public int getCountry_ID() {
        return country_ID;
    }

    /**
     *  populates the Division in the database for other methods in the Database
     * @param divisionSet populates the Division
     * @throws SQLException if error occurs
     */
    public void populateDivision(ResultSet divisionSet) throws SQLException {
        id = divisionSet.getInt(1);
        divisionName = divisionSet.getString(2);
    }

}
