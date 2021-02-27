package usova.db.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import usova.generated.Tag;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "RELATION_TAG")
public class RelationTagDao {
    @JsonIgnore
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private BigInteger id;

    @Column(name = "k")
    private String k;

    @Column(name = "v")
    private String v;

    @JsonIgnore
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "relation")
    private RelationDao relationDao;

    public RelationTagDao(Tag tag, RelationDao relationDao) {
        this.k = tag.getK();
        this.v = tag.getV();

        this.relationDao = relationDao;
    }

    public RelationTagDao() {

    }

    public void setV(String v) {
        this.v = v;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    public String getK() {
        return k;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

    public RelationDao getRelationDao() {
        return relationDao;
    }

    public void setRelationDao(RelationDao relationDao) {
        this.relationDao = relationDao;
    }
}
