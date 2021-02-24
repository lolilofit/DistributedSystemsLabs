package usova.db.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usova.db.dao.NodeDao;
import usova.db.dao.TagDao;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class TagRepository extends DbRepository<TagDao> {
    static final Logger logger = LogManager.getLogger(TagRepository.class.getName());

    public TagRepository() throws SQLException, ClassNotFoundException {
        super();
        insertStatement = connection.prepareCall("INSERT INTO NODE_TAG (k, v, nodeId) VALUES (?, ?, ?)");
        getByIdStatement = connection.prepareCall("SELECT * FROM NODE_TAG WHERE NODEID = ?");
    }

    @Override
    public void saveWithPreparedStatement(TagDao o) {
        try {
            if (o.getK() != null)
                insertStatement.setString(1, o.getK());
            else
                insertStatement.setNull(1, Types.NULL);

            if (o.getV() != null)
                insertStatement.setString(2, o.getV());
            else
                insertStatement.setNull(2, Types.NULL);

            if (o.getRelatedId() != null)
                insertStatement.setLong(3, o.getRelatedId().longValue());
            else
                insertStatement.setNull(3, Types.NULL);

            insertStatement.execute();

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void saveWithExecuteQuery(TagDao o) {
        try {
            connection.createStatement()
                    .execute(String.format("INSERT INTO NODE_TAG (k, v, nodeId) VALUES ('%s', '%s', %s)", o.getK(), o.getV(), o.getRelatedId()));
        } catch (SQLException | NullPointerException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void saveWithBatch(TagDao o) {
        try {
            batchInsertStatement.addBatch(
                    String.format("INSERT INTO NODE_TAG (k, v, nodeId) VALUES ('%s', '%s', %s)", o.getK(), o.getV(), o.getRelatedId()));
            batchSize++;
            if (batchSize > 1000)
                flushBatch();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<TagDao> getById(BigInteger id) {
        if(id == null)
            return null;

        try {
            getByIdStatement.setLong(1, id.longValue());
            ResultSet result = getByIdStatement.executeQuery();

            List<TagDao> tags = new ArrayList<>();

            while(result.next())
                tags.add(TagDao.extractFromResultSet(result));

            return tags;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
