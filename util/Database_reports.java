package util;

import Database_Models.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates the Reports utiliy to query Database
 */
public class Database_reports {

    /**
     * returns the  ObservableList of all appointments by user's chosen Month and Type
     * @return r1List
     */
    public static ObservableList<Report> Appt_by_monthtype() {
        ObservableList<Report> reportList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Type, MONTHNAME(Start) as Month, count(*) AS Count " +
                    "FROM appointments GROUP BY Type, MONTHNAME(Start) ORDER BY Type, Start;";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String Type = rs.getString("Type");
                String Month = rs.getString("Month");
                int Count = rs.getInt("Count");


                Report report = new Report(Type,Month,Count);
                reportList.add(report);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return reportList;

    }

}
