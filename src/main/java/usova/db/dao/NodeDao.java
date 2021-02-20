package usova.db.dao;

import usova.generated.Node;

import java.math.BigInteger;
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

    public NodeDao(Node node) throws SQLException, ClassNotFoundException {
        this.id = node.getId();
        this.lat = node.getLat();
        this.lon = node.getLon();
        this.user = node.getUser();
        this.uid = node.getUid();
        this.visible = node.isVisible();
        this.version = node.getVersion();
        this.changeset = node.getChangeset();
        this.timestamp = new Timestamp(node.getTimestamp().toGregorianCalendar().getTimeInMillis());

        this.tags = new ArrayList<>();

        if (node.getTag() != null)
            node.getTag().forEach(tag -> {
                try {
                    tags.add(new TagDao(tag, id));
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            });
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
}
