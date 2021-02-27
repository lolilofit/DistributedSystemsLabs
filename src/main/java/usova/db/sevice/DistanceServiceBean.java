package usova.db.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usova.db.dao.NodeDao;
import usova.db.repository.NodeJpaRepository;

import java.util.Collection;

@Service
public class DistanceServiceBean implements DistanceService {
    @Autowired
    private NodeJpaRepository nodeJpaRepository;

    @Override
    public NodeDao[] getNodeInRadius(Float x, Float y, Float r) {
        Collection<NodeDao> d = nodeJpaRepository.getAllByDistance(x, y, r);
        return d.toArray(new NodeDao[0]);
    }
}
