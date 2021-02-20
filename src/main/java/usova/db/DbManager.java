package usova.db;

import java.sql.Connection;
import java.sql.SQLException;

public class DbManager {
    Connection connection;

    public DbManager() throws SQLException, ClassNotFoundException {
        connection = PostgreConnectionManager.getConnection();
        initDb();
    }

    public void initDb() throws SQLException {
        connection.createStatement().executeQuery("DROP IF EXISTS TABLE Tag");
        connection.createStatement().executeQuery("DROP IF EXISTS TABLE Node");

        connection.createStatement().executeQuery("CREATE TABLE Node (id BIGINT, lat DECIMAL, lon DECIMAL, _user VARCHAR(2000), uid BIGINT, visible BOOLEAN, version BIGINT, changeset BIGINT, _timestamp TIMESTAMP, PRIMARY KEY (id))");

        connection.createStatement().executeQuery("CREATE TABLE Tag (k VARCHAR (2000), v VARCHAR (2000), PRIMARY KEY (k), nodeId BIGINT, FOREIGN KEY (nodeId) REFERENCES Node(id))");
    }
}
