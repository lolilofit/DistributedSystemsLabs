package usova.db;

import java.sql.Connection;
import java.sql.SQLException;

public class DbManager {
    public static void initDb() {
        try {
            Connection connection = PostgreConnectionManager.getConnection();
            connection.createStatement().execute("DROP TABLE IF EXISTS NODE_TAG");
            connection.createStatement().execute("DROP TABLE IF EXISTS RELATION_TAG");
            connection.createStatement().execute("DROP TABLE IF EXISTS WAY_TAG");
            connection.createStatement().execute("DROP TABLE IF EXISTS NODE CASCADE ");
            connection.createStatement().execute("DROP TABLE IF EXISTS OSM CASCADE ");
            connection.createStatement().execute("DROP TABLE IF EXISTS BOUNDS CASCADE ");
            connection.createStatement().execute("DROP TABLE IF EXISTS ND");
            connection.createStatement().execute("DROP TABLE IF EXISTS WAY");
            connection.createStatement().execute("DROP TABLE IF EXISTS RELATION CASCADE ");
            connection.createStatement().execute("DROP TABLE IF EXISTS MEMBER CASCADE ");

            connection.createStatement().execute("CREATE TABLE BOUNDS (id BIGINT, minlat DECIMAL, minlon DECIMAL , maxlat DECIMAL, maxlon DECIMAL, PRIMARY KEY (id))");

            connection.createStatement().execute("CREATE TABLE OSM (id BIGINT, bounds BIGINT, version DECIMAL , generator VARCHAR (2000), PRIMARY KEY (id), FOREIGN KEY (bounds) REFERENCES BOUNDS (id))");

            connection.createStatement().execute("CREATE TABLE NODE (id BIGINT, lat DECIMAL, lon DECIMAL, _user VARCHAR(2000), uid BIGINT, visible BOOLEAN, version BIGINT, changeset BIGINT, _timestamp TIMESTAMP, osm BIGINT, PRIMARY KEY (id), FOREIGN KEY (osm) REFERENCES OSM (id))");

            connection.createStatement().execute("CREATE TABLE NODE_TAG (k VARCHAR (2000) NOT NULL, v VARCHAR (2000) NOT NULL, nodeId BIGINT NOT NULL, FOREIGN KEY (nodeId) REFERENCES NODE (id))");

            connection.createStatement().execute("CREATE TABLE RELATION (id BIGINT, _user VARCHAR(2000), uid BIGINT, visible BOOLEAN, version BIGINT, changeset BIGINT, _timestamp TIMESTAMP, osmId BIGINT, PRIMARY KEY (id), FOREIGN KEY (osmId) REFERENCES OSM (id))");

            connection.createStatement().execute("CREATE TABLE RELATION_TAG (k VARCHAR (2000) NOT NULL, v VARCHAR (2000) NOT NULL, relationId BIGINT NOT NULL, FOREIGN KEY (relationId) REFERENCES RELATION (id))");

            connection.createStatement().execute("CREATE TABLE MEMBER (type VARCHAR(2000), ref BIGINT, role VARCHAR (2000), relation BIGINT, FOREIGN KEY (relation) REFERENCES RELATION (id))");

            connection.createStatement().execute("CREATE TABLE WAY (id BIGINT, _user VARCHAR(2000), uid BIGINT, visible BOOLEAN, version BIGINT, changeset BIGINT, _timestamp TIMESTAMP, osm BIGINT, PRIMARY KEY (id), FOREIGN KEY (osm) REFERENCES OSM (id))");

            connection.createStatement().execute("CREATE TABLE WAY_TAG (k VARCHAR (2000) NOT NULL, v VARCHAR (2000) NOT NULL, wayId BIGINT NOT NULL, FOREIGN KEY (wayId) REFERENCES WAY (id))");

            connection.createStatement().execute("CREATE TABLE ND (ref BIGINT, wayId BIGINT, FOREIGN  KEY (wayId) REFERENCES WAY (id))");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
