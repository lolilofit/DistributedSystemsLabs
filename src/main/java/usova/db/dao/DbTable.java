package usova.db.dao;

import usova.db.PostgreConnectionManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DbTable {
    protected PreparedStatement insert;

    protected Statement batchInsert;

    protected int batchSize;

    public DbTable() throws SQLException, ClassNotFoundException {
        batchInsert = PostgreConnectionManager.getConnection().createStatement();
    }

    public abstract void saveWithPreparedStatement() throws SQLException;

    public abstract void saveWithExecuteQuery() throws SQLException, ClassNotFoundException;

    public abstract void saveWithBatch() throws SQLException;

    public void flushBatch() throws SQLException {
        batchInsert.executeBatch();
        batchSize = 0;
    }
}
