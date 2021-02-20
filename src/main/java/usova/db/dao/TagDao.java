package usova.db.dao;

import usova.generated.Tag;

import java.math.BigInteger;
import java.sql.SQLException;

public class TagDao {
    private String k;

    private String v;

    private BigInteger nodeId;

    public TagDao(Tag tag, BigInteger nodeId) throws SQLException, ClassNotFoundException {
        this.k = tag.getK();
        this.v = tag.getV();
        this.nodeId = nodeId;
    }

    public String getK() {
        return k;
    }

    public String getV() {
        return v;
    }

    public BigInteger getNodeId() {
        return nodeId;
    }
}
