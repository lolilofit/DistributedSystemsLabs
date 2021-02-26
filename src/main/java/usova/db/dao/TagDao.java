package usova.db.dao;

import usova.generated.Tag;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "NODE_TAG")
public class TagDao {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private BigInteger id;

    @Column(name = "k")
    private String k;

    @Column(name = "v")
    private String v;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "NODEID")
    private NodeDao nodeId;

    public TagDao(Tag tag, NodeDao nodeId) {
        this.k = tag.getK();
        this.v = tag.getV();

        this.nodeId = nodeId;
    }

    public TagDao() {

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

    public NodeDao getNodeId() {
        return nodeId;
    }

    public void setNodeId(NodeDao nodeId) {
        this.nodeId = nodeId;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }
}
