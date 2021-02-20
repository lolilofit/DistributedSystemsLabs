package usova.db.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usova.db.PostgreConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DbRepository<T> {
    protected static final Logger logger = LogManager.getLogger(DbRepository.class.getName());

    protected PreparedStatement insert;

    protected Statement batchInsert;

    protected int batchSize;

    protected Connection connection;

    public DbRepository() throws SQLException, ClassNotFoundException {
        batchInsert = PostgreConnectionManager.getConnection().createStatement();
        connection = PostgreConnectionManager.getConnection();
    }

    public abstract void saveWithPreparedStatement(T o);

    public abstract void saveWithExecuteQuery(T o);

    public abstract void saveWithBatch(T o);

    public void flushBatch() {
        try {
            batchInsert.executeBatch();
            batchSize = 0;
            batchInsert.clearBatch();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
