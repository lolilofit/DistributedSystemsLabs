package usova.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usova.ArchiveDecompressor;
import usova.db.dao.NodeDao;
import usova.db.repository.jpa.NodeRepository;
import usova.generated.Node;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

@Service
public class TimeMeasureImpl {
    private static final Logger logger = LogManager.getLogger(TimeMeasureImpl.class.getName());

    private static final int MAX_NODES = 7000;

    private final NodeRepository nodeRepository;

    @Autowired
    public TimeMeasureImpl(NodeRepository nodeRepository) throws SQLException, ClassNotFoundException {
        this.nodeRepository = nodeRepository;
    }

    @PostConstruct
    public void countSavingNodesSeconds() {
        DbManager.initDb();

        int nodesCount = 0;
        long totalTime = 0;

        ArchiveDecompressor archiveDecompressor = new ArchiveDecompressor();

        Node node = archiveDecompressor.getNextNode();
        while (node != null) {
            NodeDao nodeDao = new NodeDao(node);

            long startTime = System.currentTimeMillis();
            try {
                nodeRepository.save(nodeDao);
            } catch (Exception e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();

            totalTime += endTime - startTime;

            nodesCount++;
            if(nodesCount >= MAX_NODES)
                break;

            node = archiveDecompressor.getNextNode();
        }
        archiveDecompressor.close();
    }
}
