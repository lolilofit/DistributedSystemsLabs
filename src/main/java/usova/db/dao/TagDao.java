package usova.db.dao;

import usova.db.PostgreConnectionManager;
import usova.generated.Tag;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TagDao extends DbTable {
    private String k;

    private String v;

    private BigInteger nodeId;

    public TagDao(Tag tag, BigInteger nodeId) throws SQLException, ClassNotFoundException {
        super();

        this.k = tag.getK();
        this.v = tag.getV();
        this.nodeId = nodeId;

        insert = PostgreConnectionManager.getConnection().prepareCall("INSERT INTO Tag (k, v, nodeId) VALUES (?, ?, ?)");
    }

    @Override
    public void saveWithPreparedStatement() throws SQLException {
        insert.setString(1, k);
        insert.setString(2, v);
        insert.setLong(3, nodeId.longValue());

        insert.execute();
    }

    @Override
    public void saveWithExecuteQuery() throws SQLException, ClassNotFoundException {
        PostgreConnectionManager.getConnection().createStatement()
                .executeQuery(String.format("INSERT INTO Tag (k, v) VALUES (%s, %s, %s)", k, v, nodeId));
    }

    @Override
    public void saveWithBatch() throws SQLException {
        if(batchSize < 100) {
            batchInsert.addBatch(
                    String.format("INSERT INTO Tag (k, v) VALUES (%s, %s, %s)", k, v, nodeId));
            batchSize++;
        }
        else
            flushBatch();
    }
}
