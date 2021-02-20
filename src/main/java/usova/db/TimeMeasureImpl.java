package usova.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usova.ArchiveDecompressor;
import usova.db.dao.NodeDao;
import usova.db.repository.NodeRepository;
import usova.generated.Node;

import java.sql.SQLException;
import java.util.function.Consumer;

public class TimeMeasureImpl {
    private static final Logger logger = LogManager.getLogger(TimeMeasureImpl.class.getName());

    private static final int MAX_NODES = 7000;

    private final NodeRepository nodeRepository;

    public TimeMeasureImpl() throws SQLException, ClassNotFoundException {
        nodeRepository = new NodeRepository();
    }

    public double countSavingNodesSeconds(Consumer<NodeDao> consumer) throws SQLException, ClassNotFoundException {
        DbManager dbManager = new DbManager();
        int nodesCount = 0;
        long totalTime = 0;

        ArchiveDecompressor archiveDecompressor = new ArchiveDecompressor();

        Node node = archiveDecompressor.getNextNode();
        while (node != null) {
            NodeDao nodeDao = new NodeDao(node);

            long startTime = System.currentTimeMillis();
            consumer.accept(nodeDao);
            long endTime = System.currentTimeMillis();

            totalTime += endTime - startTime;

            nodesCount++;
            if(nodesCount >= MAX_NODES)
                break;

            node = archiveDecompressor.getNextNode();
        }
        archiveDecompressor.close();

        return (double) totalTime / 1000.0;
    }

    public void measureSaveTime() throws SQLException, ClassNotFoundException {
        double executeQueryTime = countSavingNodesSeconds(nodeRepository::saveWithExecuteQuery);
        logger.info(String.format("SAVE WITH EXECUTE QUERY TIME = %s (rows/sec)", MAX_NODES / executeQueryTime));

        double preparedTime = countSavingNodesSeconds(nodeRepository::saveWithPreparedStatement);
        logger.info(String.format("SAVE WITH PREPARED STATEMENT TIME = %s (rows/sec)", MAX_NODES / preparedTime));

        double batchTime = countSavingNodesSeconds(nodeRepository::saveWithBatch);
        long startTime = System.currentTimeMillis();
        nodeRepository.flushBatch();
        long endTime = System.currentTimeMillis();

        batchTime += (double) (endTime - startTime) / 1000.0;
        logger.info(String.format("SAVE WITH BATCH TIME = %s (rows/sec)",  MAX_NODES / batchTime));
    }
}
