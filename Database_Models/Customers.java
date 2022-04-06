package Database_Models;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *  creates the Customer database model
 */
public class Customers{
    private int id;
    private String name;
    private String address;
    private String zipcode;
    private String phone;
    public int divisionID;

    /**
     *
     * @param id Customer ID Setter
     * @param name Customer name Setter
     * @param address Customer address Setter
     * @param zipcode Customer Zipcode Setter
     * @param phone customer phone Setter
     * @param divisionID the division ID Setter
     */
    public Customers(int id, String name, String address, String zipcode,
                     String phone, int divisionID) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.zipcode = zipcode;
        this.phone = phone;
        this.divisionID = divisionID;
    }

    /**
     * getter for the customer ID
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     *getter for the customer Name
     *@return name
     */
    public String getName() {
        return name;
    }

    /**
     *getter for the customer  address
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     *getter for the customer Zipcode
     * @return zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     *@return getter for the customer phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     *getter for the customer division ID
     * @return division ID
     */
    public int getDivisionID () {
        return divisionID;
    }


    /**
     *
     * @param resultSet Populates Customers in a result set
     * @throws SQLException if error occurs
     */
    public void populateCustomer(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt(1);
        name = resultSet.getString(2);
        address = resultSet.getString(3);
        zipcode = resultSet.getString(4);
        phone = resultSet.getString(5);
        divisionID = resultSet.getInt(6);
    }

}
