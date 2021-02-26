package usova.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreConnectionManager {
    static final Logger logger = LogManager.getLogger(PostgreConnectionManager.class.getName());

    private static volatile Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if(connection == null) {
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "123");

            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/osm", props);
            logger.debug("CONNECTED");
        }
        return connection;
    }
}
