package usova.db.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import usova.db.dao.NodeDao;

import java.math.BigInteger;

public interface NodeRepository extends JpaRepository<NodeDao, BigInteger> {
}
