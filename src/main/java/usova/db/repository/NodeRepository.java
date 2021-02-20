package usova.db.repository;

import usova.db.dao.DbTable;
import usova.db.dao.NodeDao;
import usova.db.dao.TagDao;

import java.sql.SQLException;
import java.sql.Types;


public class NodeRepository extends DbTable<NodeDao> {
    private TagRepository tagRepository;

    public NodeRepository() throws SQLException, ClassNotFoundException {
        super();
        insert = connection.prepareCall("INSERT INTO Node (id, lat, lon, _user, uid, visible, version, changeset, _timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        tagRepository = new TagRepository();
    }

    @Override
    public void saveWithPreparedStatement(NodeDao o) {
        try {
            if (o.getId() != null)
                insert.setLong(1, o.getId().longValue());
            else
                insert.setNull(1, Types.NULL);

            if (o.getLat() != null)
                insert.setDouble(2, o.getLat());
            else
                insert.setNull(2, Types.NULL);

            if (o.getLon() != null)
                insert.setDouble(3, o.getLon());
            else
                insert.setNull(3, Types.NULL);

            if (o.getUser() != null)
                insert.setString(4, o.getUser());
            else
                insert.setNull(4, Types.NULL);

            if (o.getUid() != null)
                insert.setLong(5, o.getUid().longValue());
            else
                insert.setNull(5, Types.NULL);

            if (o.getVisible() != null)
                insert.setBoolean(6, o.getVisible());
            else
                insert.setNull(6, Types.NULL);

            if (o.getVersion() != null)
                insert.setLong(7, o.getVersion().longValue());
            else
                insert.setNull(7, Types.NULL);

            if (o.getChangeset() != null)
                insert.setLong(8, o.getChangeset().longValue());
            else
                insert.setNull(8, Types.NULL);

            if (o.getTimestamp() != null)
                insert.setTimestamp(9, o.getTimestamp());
            else
                insert.setNull(9, Types.NULL);

            insert.execute();

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
                            "INSERT INTO Node (id, lat, lon, _user, uid, visible, version, changeset, _timestamp) VALUES (%s, %s, %s, '%s', %s, %s, %s, %s, '%s')",
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
            batchInsert.addBatch(String.format(
                    "INSERT INTO Node (id, lat, lon, _user, uid, visible, version, changeset, _timestamp) VALUES (%s, %s, %s, '%s', %s, %s, %s, %s, '%s')",
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
}
