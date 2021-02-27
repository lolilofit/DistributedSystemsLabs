package usova.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import usova.db.dao.RelationDao;

import java.math.BigInteger;

public interface RelationJpaRepository extends JpaRepository<RelationDao, BigInteger> {
    @Query("select r from RelationDao  r where r.id = ?1")
    RelationDao getRelationDaoById(BigInteger id);
}
