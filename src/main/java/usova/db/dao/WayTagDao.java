package usova.db.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import usova.generated.Tag;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "WAY_TAG")
public class WayTagDao {
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
    @JoinColumn(name = "WAYID")
    private WayDao way;

    public WayTagDao(Tag tag, WayDao way) {
        this.k = tag.getK();
        this.v = tag.getV();
        this.way = way;
    }

    public WayTagDao() {

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

    public WayDao getWay() {
        return way;
    }

    public void setWay(WayDao way) {
        this.way = way;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }
}
