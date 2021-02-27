package usova.db.dao;

import usova.generated.Node;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "Node")
public class NodeDao {
    @Id
    @Column(name = "id")
    protected BigInteger id;

    @Column(name = "lat")
    protected Double lat;

    @Column(name = "lon")
    protected Double lon;

    @Column(name = "_user")
    protected String user;

    @Column(name = "uid")
    protected BigInteger uid;

    @Column(name = "visible")
    protected Boolean visible;

    @Column(name = "version")
    protected BigInteger version;

    @Column(name = "changeset")
    protected BigInteger changeset;

    @Column(name = "_timestamp")
    protected Timestamp timestamp;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "nodeId", cascade = CascadeType.ALL)
    protected Collection<NodeTagDao> tags;

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
            node.getTag().forEach(t -> tags.add(new NodeTagDao(t, this)));
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

    public Collection<NodeTagDao> getTags() {
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

    public void setTags(Collection<NodeTagDao> tags) {
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
