package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Creates DBQuery Class
 */
public class DBQuery {

    private static PreparedStatement preparedStatement;

    /**
     *
     * @param conn requires the connection
     * @param sqlStatement requires a sql statement
     * @throws SQLException in the event sql is violated
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        preparedStatement = conn.prepareStatement(sqlStatement);
    }



    /**
     *
     * @return get prepared statement
     */
    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

}
