package util;

import Database_Models.*;

/** this class contains some queries that will be ran when populating data to the data base
 * created by justiceChimara
 */
public class Queries {
    public static String getUserData(String username, String password) {
        return "SELECT * FROM users WHERE User_Name='" + username + "' AND Password='" + password + "'";
    }

    /**
     * getter
     * @param userID getter
     * @return UserID
     */
    public static String getUserAppointment(int userID) {
        return "select * from appointments";
        // return "select * from appointments where User_ID =" + id +" and MONTH(Start) = MONTH(CURRENT_DATE()) and YEAR(Start) = YEAR(CURRENT_DATE());";
    }

    /**
     * getter
     * @return Customers
     */
    public static String getCustomers() {
        return "select * from customers";
    }


    public static String getDivisions(int countryId) {
        return "select * from first_level_divisions where Country_ID = "+countryId;
    }

    public static String getAllDivisions() {
        return "select * from first_level_divisions";
    }


    public static String getDivisionsByID(int id) {
        return "select * from first_level_divisions where Division_ID = "+id;
    }

    public static String getCountry()
    {
        return "select * from countries";
    }

    public static String deleteCustomer(int id)
    {
        return "delete from customers where Customer_id = "+id;
    }

    public static String deleteAppointment(int id) {
        return "delete from appointments where Appointment_ID = "+id;
    }

    public static String getContacts(){
        return "select * from contacts";
    }


}