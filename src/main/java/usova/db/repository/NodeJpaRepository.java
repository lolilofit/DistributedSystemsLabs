package usova.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import usova.db.dao.NodeDao;

import java.math.BigInteger;
import java.util.Collection;

public interface NodeJpaRepository extends JpaRepository<NodeDao, BigInteger> {
    @Query("SELECT n from NodeDao n where n.id = ?1")
    NodeDao getNodeDaoById(BigInteger id);

    @Query(value = "SELECT n FROM NodeDao n WHERE gc_to_sec(earth_distance(ll_to_earth(?1, ?2), ll_to_earth(n.lat, n.lon))) < ?3 ORDER BY gc_to_sec(earth_distance(ll_to_earth(?1, ?2), ll_to_earth(n.lat, n.lon))) ASC")
    Collection<NodeDao> getAllByDistance(Float x1, Float y1, Float r);
}
