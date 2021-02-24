package usova.db.dao;

import usova.generated.Tag;

import java.math.BigInteger;
import java.sql.ResultSet;
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

    public TagDao() {

    }

    public static TagDao extractFromResultSet(ResultSet result) throws SQLException {
        TagDao tagDao = new TagDao();

        tagDao.setK(result.getString(1));
        tagDao.setV(result.getString(2));
        tagDao.setRelatedId(BigInteger.valueOf(result.getLong(3)));

        return tagDao;
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

    public void setK(String k) {
        this.k = k;
    }

    public void setRelatedId(BigInteger relatedId) {
        this.relatedId = relatedId;
    }

    public void setV(String v) {
        this.v = v;
    }
}
