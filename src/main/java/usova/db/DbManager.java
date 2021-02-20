package usova.db;

import java.sql.Connection;
import java.sql.SQLException;

public class DbManager {
    Connection connection;

    public DbManager() throws SQLException, ClassNotFoundException {
        connection = PostgreConnectionManager.getConnection();
        initDb();
    }

    public void initDb() {
        try {
            connection.createStatement().execute("DROP TABLE IF EXISTS Tag");
            connection.createStatement().execute("DROP TABLE IF EXISTS Node");

            connection.createStatement().execute("CREATE TABLE Node (id BIGINT, lat DECIMAL, lon DECIMAL, _user VARCHAR(2000), uid BIGINT, visible BOOLEAN, version BIGINT, changeset BIGINT, _timestamp TIMESTAMP, PRIMARY KEY (id))");

            connection.createStatement().execute("CREATE TABLE Tag (k VARCHAR (2000), v VARCHAR (2000), nodeId BIGINT, FOREIGN KEY (nodeId) REFERENCES Node(id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
