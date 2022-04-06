package Database_Models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  creates the Country database model
 */
public class Country {
    private int id;
    private String country;

    /**
     * Setters for the id and Country fields
     * @param id the id
     * @param country the country
     */
    public Country(int id, String country) {
        this.id = id;
        this.country = country;
    }

    /**
     *
     * @return the country ID
     */
    public int getId() {
        return id;
    }


    /**
     * getters for the country field
     * @return country
     */
public String getCountry(){return country;}

    public void setCountry(String country) {
        this.country = country;
    }


    @Override
    public String toString() {
        return country;
    }

    /**
     *
     * @param countrySet populates the country data
     * @throws SQLException if error occurs
     */
    public void populateCountry(ResultSet countrySet) throws SQLException {
        id = countrySet.getInt(1);
        country = countrySet.getString(2);
    }
}
