package usova.db.repository;

import usova.db.dao.NodeDao;
import usova.db.dao.TagDao;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;


public class NodeRepository extends DbRepository<NodeDao> {
    private TagRepository tagRepository;

    public NodeRepository() throws SQLException, ClassNotFoundException {
        super();
        insertStatement = connection.prepareCall("INSERT INTO NODE (id, lat, lon, _user, uid, visible, version, changeset, _timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        getByIdStatement = connection.prepareCall("SELECT * FROM NODE WHERE ID = ?");
        batchInsertStatement = connection.prepareCall("INSERT INTO NODE (id, lat, lon, _user, uid, visible, version, changeset, _timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        tagRepository = new TagRepository();
    }

    private void fillParams(NodeDao o, PreparedStatement statement) throws SQLException {
        if (o.getId() != null)
            statement.setLong(1, o.getId().longValue());
        else
            statement.setNull(1, Types.NULL);

        if (o.getLat() != null)
            statement.setDouble(2, o.getLat());
        else
            statement.setNull(2, Types.NULL);

        if (o.getLon() != null)
            statement.setDouble(3, o.getLon());
        else
            statement.setNull(3, Types.NULL);

        if (o.getUser() != null)
            statement.setString(4, o.getUser());
        else
            statement.setNull(4, Types.NULL);

        if (o.getUid() != null)
            statement.setLong(5, o.getUid().longValue());
        else
            statement.setNull(5, Types.NULL);

        if (o.getVisible() != null)
            statement.setBoolean(6, o.getVisible());
        else
            statement.setNull(6, Types.NULL);

        if (o.getVersion() != null)
            statement.setLong(7, o.getVersion().longValue());
        else
            statement.setNull(7, Types.NULL);

        if (o.getChangeset() != null)
            statement.setLong(8, o.getChangeset().longValue());
        else
            statement.setNull(8, Types.NULL);

        if (o.getTimestamp() != null)
            statement.setTimestamp(9, o.getTimestamp());
        else
            statement.setNull(9, Types.NULL);
    }
    @Override
    public void saveWithPreparedStatement(NodeDao o) {
        try {
            fillParams(o, insertStatement);
            insertStatement.execute();

            for (TagDao tag : o.getTags()) {
                tagRepository.saveWithPreparedStatement(tag);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void saveWithExecuteQuery(NodeDao o) {
        try {
            connection.createStatement()
                    .execute(String.format(
                            "INSERT INTO NODE (id, lat, lon, _user, uid, visible, version, changeset, _timestamp) VALUES (%s, %s, %s, '%s', %s, %s, %s, %s, '%s')",
                            o.getId(), o.getLat(), o.getLon(), o.getUser(), o.getUid(), o.getVisible(), o.getVersion(), o.getChangeset(), o.getTimestamp())
                    );

            for (TagDao tag : o.getTags()) {
                tagRepository.saveWithExecuteQuery(tag);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void saveWithBatch(NodeDao o) {
        try {
            fillParams(o, batchInsertStatement);
            batchInsertStatement.addBatch();
            batchSize++;

            for (TagDao tag : o.getTags()) {
                tagRepository.saveWithBatch(tag);
            }
            if (batchSize > 1000) {
                flushBatch();
                tagRepository.flushBatch();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void flushBatch() {
        super.flushBatch();
        tagRepository.flushBatch();
    }

    @Override
    public List<NodeDao> getById(BigInteger id) {
        if(id == null)
            return null;

        try {
            getByIdStatement.setLong(1, id.longValue());
            ResultSet result = getByIdStatement.executeQuery();
            if(!result.next())
                return null;

            return List.of(NodeDao.extractFromResultSet(result));
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
