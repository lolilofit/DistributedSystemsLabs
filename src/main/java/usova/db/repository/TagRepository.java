package usova.db.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usova.db.dao.DbTable;
import usova.db.dao.TagDao;

import java.sql.SQLException;
import java.sql.Types;

public class TagRepository extends DbTable<TagDao> {
    static final Logger logger = LogManager.getLogger(TagRepository.class.getName());

    private int count = 1;

    public TagRepository() throws SQLException, ClassNotFoundException {
        super();
        insert = connection.prepareCall("INSERT INTO Tag (k, v, nodeId) VALUES (?, ?, ?)");
    }

    @Override
    public void saveWithPreparedStatement(TagDao o) {
        try {
            if (o.getK() != null)
                insert.setString(1, o.getK());
            else
                insert.setNull(1, Types.NULL);

            if (o.getV() != null)
                insert.setString(2, o.getV());
            else
                insert.setNull(2, Types.NULL);

            if (o.getNodeId() != null)
                insert.setLong(3, o.getNodeId().longValue());
            else
                insert.setNull(3, Types.NULL);

            insert.execute();
            count = 1;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void saveWithExecuteQuery(TagDao o) {
        try {
            connection.createStatement()
                    .execute(String.format("INSERT INTO Tag (k, v, nodeId) VALUES ('%s', '%s', %s)", o.getK(), o.getV(), o.getNodeId()));
        } catch (SQLException | NullPointerException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void saveWithBatch(TagDao o) {
        try {
            batchInsert.addBatch(
                    String.format("INSERT INTO Tag (k, v, nodeId) VALUES ('%s', '%s', %s)", o.getK(), o.getV(), o.getNodeId()));
            batchSize++;
            if (batchSize > 1000)
                flushBatch();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
