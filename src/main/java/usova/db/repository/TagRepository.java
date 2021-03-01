package usova.db.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usova.db.dao.NodeDao;
import usova.db.dao.TagDao;

import java.math.BigInteger;
import java.sql.PreparedStatement;
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
        batchInsertStatement = connection.prepareCall("INSERT INTO NODE_TAG (k, v, nodeId) VALUES (?, ?, ?)");
    }

    private void fillParams(TagDao o, PreparedStatement statement) throws SQLException {
        if (o.getK() != null)
            statement.setString(1, o.getK());
        else
            statement.setNull(1, Types.NULL);

        if (o.getV() != null)
            statement.setString(2, o.getV());
        else
            statement.setNull(2, Types.NULL);

        if (o.getRelatedId() != null)
            statement.setLong(3, o.getRelatedId().longValue());
        else
            statement.setNull(3, Types.NULL);
    }

    @Override
    public void saveWithPreparedStatement(TagDao o) {
        try {
            fillParams(o, insertStatement);
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
            fillParams(o, batchInsertStatement);
            batchInsertStatement.addBatch();
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
