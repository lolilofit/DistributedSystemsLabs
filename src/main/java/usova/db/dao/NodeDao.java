package usova.db.dao;

import usova.generated.Node;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Node")
public class NodeDao {
    @Id
    @Column(name = "id")
    private BigInteger id;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @Column(name = "_user")
    private String user;

    @Column(name = "uid")
    private BigInteger uid;

    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "version")
    private BigInteger version;

    @Column(name = "changeset")
    private BigInteger changeset;

    @Column(name = "_timestamp")
    private Timestamp timestamp;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "nodeId", cascade = CascadeType.ALL)
    private Collection<TagDao> tags;

    public NodeDao(Node node) {
        this.id = node.getId();
        this.lat = node.getLat();
        this.lon = node.getLon();
        this.user = node.getUser();
        this.uid = node.getUid();
        this.visible = node.isVisible();
        this.version = node.getVersion();
        this.changeset = node.getChangeset();
        this.timestamp = new Timestamp(node.getTimestamp().toGregorianCalendar().getTimeInMillis());

        tags = new ArrayList<>();
        if(node.getTag() != null)
            node.getTag().forEach(t -> tags.add(new TagDao(t, this)));
    }

    public NodeDao() {}

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

    public Collection<TagDao> getTags() {
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

    public void setChangeset(BigInteger changeset) {
        this.changeset = changeset;
    }

    public void setTags(Collection<TagDao> tags) {
        this.tags = tags;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
