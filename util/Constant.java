package util;

import java.sql.Connection;

/**
 * created by justice chimara
 * public variables that are used to identify the database
 */
public class Constant
{
    // public variables that are used to identify the database.
    public static final String JDBC_CONNECTION_URL = "jdbc:mysql://localhost/client_schedule?connectionTimeZone = SERVER";
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // Driver reference
    public static final String USERNAME = "sqlUser"; // Username
    public static final String PASSWORD = "Passw0rd!"; // Password
}
