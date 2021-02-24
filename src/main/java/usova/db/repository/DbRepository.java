package usova.db.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usova.db.PostgreConnectionManager;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class DbRepository<T> {
    protected static final Logger logger = LogManager.getLogger(DbRepository.class.getName());

    protected PreparedStatement insertStatement;

    protected PreparedStatement getByIdStatement;

    protected Statement batchInsertStatement;

    protected int batchSize;

    protected Connection connection;

    public DbRepository() throws SQLException, ClassNotFoundException {
        batchInsertStatement = PostgreConnectionManager.getConnection().createStatement();
        connection = PostgreConnectionManager.getConnection();
    }

    public abstract void saveWithPreparedStatement(T o);

    public abstract void saveWithExecuteQuery(T o);

    public abstract void saveWithBatch(T o);

    public abstract List<T> getById(BigInteger id);

    public void flushBatch() {
        try {
            batchInsertStatement.executeBatch();
            batchSize = 0;
            batchInsertStatement.clearBatch();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
