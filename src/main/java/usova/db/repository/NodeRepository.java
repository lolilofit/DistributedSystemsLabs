package usova.db.repository;

import usova.db.dao.NodeDao;
import usova.db.dao.TagDao;

import java.math.BigInteger;
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
        tagRepository = new TagRepository();
    }

    @Override
    public void saveWithPreparedStatement(NodeDao o) {
        try {
            if (o.getId() != null)
                insertStatement.setLong(1, o.getId().longValue());
            else
                insertStatement.setNull(1, Types.NULL);

            if (o.getLat() != null)
                insertStatement.setDouble(2, o.getLat());
            else
                insertStatement.setNull(2, Types.NULL);

            if (o.getLon() != null)
                insertStatement.setDouble(3, o.getLon());
            else
                insertStatement.setNull(3, Types.NULL);

            if (o.getUser() != null)
                insertStatement.setString(4, o.getUser());
            else
                insertStatement.setNull(4, Types.NULL);

            if (o.getUid() != null)
                insertStatement.setLong(5, o.getUid().longValue());
            else
                insertStatement.setNull(5, Types.NULL);

            if (o.getVisible() != null)
                insertStatement.setBoolean(6, o.getVisible());
            else
                insertStatement.setNull(6, Types.NULL);

            if (o.getVersion() != null)
                insertStatement.setLong(7, o.getVersion().longValue());
            else
                insertStatement.setNull(7, Types.NULL);

            if (o.getChangeset() != null)
                insertStatement.setLong(8, o.getChangeset().longValue());
            else
                insertStatement.setNull(8, Types.NULL);

            if (o.getTimestamp() != null)
                insertStatement.setTimestamp(9, o.getTimestamp());
            else
                insertStatement.setNull(9, Types.NULL);

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
            batchInsertStatement.addBatch(String.format(
                    "INSERT INTO NODE (id, lat, lon, _user, uid, visible, version, changeset, _timestamp) VALUES (%s, %s, %s, '%s', %s, %s, %s, %s, '%s')",
                    o.getId(), o.getLat(), o.getLon(), o.getUser(), o.getUid(), o.getVisible(), o.getVersion(), o.getChangeset(), o.getTimestamp()));
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
