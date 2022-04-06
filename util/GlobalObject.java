package util;

import Database_Utils.DatabaseUsers;
import Database_Models.Login_users;
import Database_Models.Users;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * used for the login of a user
 */
public class GlobalObject {
    public static final Login_users user = new Login_users();
    public static ObservableList<Users> allusers;

    static {
        try {
            allusers = DatabaseUsers.getDBUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static LocalDateTime convertToutc(LocalDateTime myDateTime) {
        ZonedDateTime myZDT = ZonedDateTime.of(myDateTime, ZoneId.systemDefault());
        ZoneId utc = ZoneId.of("America/New_York");
        ZonedDateTime easternZDT = ZonedDateTime.ofInstant(myZDT.toInstant(), utc);
        return easternZDT.toLocalDateTime();
    }
}
