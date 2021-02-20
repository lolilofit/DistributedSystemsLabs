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
