package Database_Utils;

import util.DatabaseConnection;
import Database_Models.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;

/**
 * Method to Query Database Appointment
 */
public class DatabaseAppointments {
    /**
     *
     * @return All Appointments from the Database and easy to access when adding an or displaying appointments
     * @throws SQLException if exception has occurred
     */
    public static ObservableList<Appointments> getDBAppointments() throws SQLException {
        ObservableList<Appointments> DBAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * from appointments";
        PreparedStatement prestatement = DatabaseConnection.getConnection().prepareStatement(sql);
        ResultSet results = prestatement.executeQuery();
        while (results.next()) {
            int appointmentID = results.getInt("Appointment_ID");
            String Title = results.getString("Title");
            String description = results.getString("Description");
            String location = results.getString("Location");
            String type = results.getString("Type");

            // times converted to local time of user
            LocalDateTime startDate = results.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDate = results.getTimestamp("End").toLocalDateTime();

            int customerID = results.getInt("Customer_ID");
            int userID = results.getInt("User_ID");
            int contactID = results.getInt("Contact_ID");
            Appointments App = new Appointments(appointmentID, Title, description,
                    location, type, startDate, endDate, customerID, userID, contactID);
            DBAppointments.add(App);
        }
        return DBAppointments;
    }
}
