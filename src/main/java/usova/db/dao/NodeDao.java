package usova.db.dao;

import usova.db.PostgreConnectionManager;
import usova.generated.Node;

import java.math.BigInteger;
import java.sql.*;
import java.util.List;

public class NodeDao extends DbTable {
    private BigInteger id;

    private Double lat;

    private Double lon;

    private String user;

    private BigInteger uid;

    private Boolean visible;

    private BigInteger version;

    private BigInteger changeset;

    private Timestamp timestamp;

    private List<TagDao> tags;

    private PreparedStatement insert;

    public NodeDao(Node node) throws SQLException, ClassNotFoundException {
        super();

        this.id = node.getId();
        this.lat = node.getLat();
        this.lon = node.getLon();
        this.user = node.getUser();
        this.uid = node.getUid();
        this.visible = node.isVisible();
        this.version = node.getVersion();
        this.changeset = node.getChangeset();
        this.timestamp = new Timestamp(node.getTimestamp().toGregorianCalendar().getTimeInMillis());

        node.getTag().forEach(tag -> {
            try {
                tags.add(new TagDao(tag, id));
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });

        insert =  PostgreConnectionManager.getConnection().prepareCall("INSERT INTO Node (id, lat, lon, _user, uid, visible, version, changeset, _timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    @Override
    public void saveWithPreparedStatement() throws SQLException {
        insert.setLong(1, id.longValue());
        insert.setDouble(2, lat);
        insert.setDouble(3, lon);
        insert.setString(4, user);
        insert.setLong(5, uid.longValue());
        insert.setBoolean(6, visible);
        insert.setLong(7, version.longValue());
        insert.setLong(8, changeset.longValue());
        insert.setTimestamp(9, timestamp);

        for (TagDao tag : tags) {
            tag.saveWithPreparedStatement();
        }
    }

    @Override
    public void saveWithExecuteQuery() throws SQLException, ClassNotFoundException {
        PostgreConnectionManager.getConnection().createStatement()
                .executeQuery(String.format(
                        "INSERT INTO Node (id, lat, lon, _user, uid, visible, version, changeset, _timestamp) VALUES (%s, %s, $s, %s, %s, %s, %s, %s, %s, %s)",
                        id, lat, lon, user, uid, visible, version, changeset, timestamp)
                );

        for(TagDao tag : tags) {
            tag.saveWithExecuteQuery();
        }
    }

    @Override
    public void saveWithBatch() throws SQLException {
        if(batchSize < 100) {
            batchInsert.addBatch(String.format(
                    "INSERT INTO Node (id, lat, lon, _user, uid, visible, version, changeset, _timestamp) VALUES (%s, %s, $s, %s, %s, %s, %s, %s, %s, %s)",
                    id, lat, lon, user, uid, visible, version, changeset, timestamp));
            batchSize++;
        } else
            flushBatch();
    }
}
