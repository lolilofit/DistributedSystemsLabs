package usova.db.dao;

import usova.generated.Tag;

import java.math.BigInteger;
import java.sql.SQLException;

public class TagDao {
    private String k;

    private String v;

    private BigInteger relatedId;

    public TagDao(Tag tag, BigInteger nodeId) {
        this.k = tag.getK();
        this.v = tag.getV();
        this.relatedId = nodeId;
    }

    public String getK() {
        return k;
    }

    public String getV() {
        return v;
    }

    public BigInteger getRelatedId() {
        return relatedId;
    }
}
