package usova.db.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import usova.generated.Nd;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "ND")
public class NdDao {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    @JsonIgnore
    private BigInteger id;

    @Column(name = "ref")
    private BigInteger ref;

    @JsonIgnore
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "WAYID")
    private WayDao way;

    public NdDao(Nd nd, WayDao way) {
        this.ref = nd.getRef();
        this.way = way;
    }

    public NdDao() {}

    public BigInteger getRef() {
        return ref;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setRef(BigInteger ref) {
        this.ref = ref;
    }

    public void setWay(WayDao way) {
        this.way = way;
    }

    public BigInteger getId() {
        return id;
    }

    public WayDao getWay() {
        return way;
    }
}
