package usova.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usova.ArchiveDecompressor;
import usova.db.dao.NodeDao;
import usova.db.repository.NodeJpaRepository;
import usova.db.sevice.DistanceServiceBean;
import usova.generated.Node;

import javax.annotation.PostConstruct;

@Service
public class ExtractorService {
    private static final int MAX_NODES = 7000;

    @Autowired
    private NodeJpaRepository nodeJpaRepository;

    @Autowired
    private DistanceServiceBean distanceServiceBean;

    @PostConstruct
    public void extract() {
        DbManager.initDb();

        int nodesCount = 0;
        ArchiveDecompressor archiveDecompressor = new ArchiveDecompressor();

        Node node = archiveDecompressor.getNextNode();
        while (node != null) {
            NodeDao nodeDao = new NodeDao(node);

            nodeJpaRepository.save(nodeDao);

            //NodeDao[] n =  distanceService.getNodeInRadius(nodeDao.getLat().floatValue(), nodeDao.getLon().floatValue(), 1000.0f);
            nodesCount++;
            if (nodesCount >= MAX_NODES)
                break;

            node = archiveDecompressor.getNextNode();
        }
        archiveDecompressor.close();
    }
}
