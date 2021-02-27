package usova.db.sevice;

import usova.db.dao.NodeDao;

public interface DistanceService {
    NodeDao[] getNodeInRadius(Float x, Float y, Float r);
}
