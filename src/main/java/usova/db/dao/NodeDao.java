package usova.db.dao;

import usova.db.repository.TagRepository;
import usova.generated.Node;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NodeDao {
    private BigInteger id;

    private Double lat;

    private Double lon;

    private String user;

    private BigInteger uid;

    private Boolean visible;

    private BigInteger version;

    private BigInteger changeset;

    private Timestamp timestamp;

    private List<TagDao> tags;

    private BigInteger osmId;

    public NodeDao(Node node, BigInteger osmId) {
        this.id = node.getId();
        this.lat = node.getLat();
        this.lon = node.getLon();
        this.user = node.getUser();
        this.uid = node.getUid();
        this.visible = node.isVisible();
        this.version = node.getVersion();
        this.changeset = node.getChangeset();
        this.osmId = osmId;
        this.timestamp = new Timestamp(node.getTimestamp().toGregorianCalendar().getTimeInMillis());

        this.tags = new ArrayList<>();

        if (node.getTag() != null)
            node.getTag().forEach(tag -> tags.add(new TagDao(tag, id)));
    }

    public NodeDao() {}

    public static NodeDao extractFromResultSet(ResultSet result) throws SQLException, ClassNotFoundException {
        NodeDao nodeDao = new NodeDao();

        nodeDao.setId(BigInteger.valueOf(result.getLong(1)));
        nodeDao.setLat(result.getDouble(2));
        nodeDao.setLon(result.getDouble(3));
        nodeDao.setUser(result.getString(4));
        nodeDao.setUid(BigInteger.valueOf(result.getLong(5)));
        nodeDao.setVisible(result.getBoolean(6));
        nodeDao.setVersion(BigInteger.valueOf(result.getLong(7)));
        nodeDao.setChangeset(BigInteger.valueOf(result.getLong(8)));
        nodeDao.setTimestamp(result.getTimestamp(9));

        TagRepository tagRepository = new TagRepository();
        nodeDao.setTags(tagRepository.getById(nodeDao.getId()));

        return nodeDao;
    }

    public BigInteger getId() {
        return id;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public BigInteger getUid() {
        return uid;
    }

    public String getUser() {
        return user;
    }

    public BigInteger getChangeset() {
        return changeset;
    }

    public BigInteger getVersion() {
        return version;
    }

    public Boolean getVisible() {
        return visible;
    }

    public List<TagDao> getTags() {
        return tags;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public void setUid(BigInteger uid) {
        this.uid = uid;
    }

    public BigInteger getOsmId() {
        return osmId;
    }

    public void setChangeset(BigInteger changeset) {
        this.changeset = changeset;
    }

    public void setTags(List<TagDao> tags) {
        this.tags = tags;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setOsmId(BigInteger osmId) {
        this.osmId = osmId;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
