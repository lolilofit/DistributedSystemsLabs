package usova.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import usova.db.dao.WayDao;

import java.math.BigInteger;

public interface WayJpaRepository extends JpaRepository<WayDao, BigInteger> {
    @Query("select w from WayDao w where w.id = ?1")
    WayDao getWayDaoById(BigInteger id);
}
