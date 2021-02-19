package usova.db.dao;

import java.sql.SQLException;

public interface DbTable {
    void saveWithPreparedStatement() throws SQLException;

    void saveWithExecuteQuery() throws SQLException, ClassNotFoundException;
}
