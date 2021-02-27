package usova.db.dao;

import usova.generated.Way;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "WAY")
public class WayDao {
    @Id
    @Column(name = "id")
    private BigInteger id;

    @Column(name = "_user")
    private String user;

    @Column(name = "uid")
    private BigInteger uid;

    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "changeset")
    private BigInteger changeset;

    @Column(name = "_timestamp")
    private Timestamp timestamp;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "way", cascade = CascadeType.ALL)
    private List<NdDao> ndDaos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "way", cascade = CascadeType.ALL)
    private List<WayTagDao> tags;

    public WayDao(Way way) {
        this.id = way.getId();
        this.user = way.getUser();
        this.uid = way.getUid();
        this.visible = way.isVisible();
        this.changeset = way.getChangeset();
        this.timestamp = new Timestamp(way.getTimestamp().getMillisecond());

        this.ndDaos = new ArrayList<>();
        if (way.getNd() != null)
            way.getNd().forEach(nd -> ndDaos.add(new NdDao(nd, this)));

        this.tags = new ArrayList<>();
        if (way.getTag() != null)
            way.getTag().forEach(tag -> tags.add(new WayTagDao(tag, this)));
    }

    public WayDao() {}

    public List<NdDao> getNdDaos() {
        return ndDaos;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setUid(BigInteger uid) {
        this.uid = uid;
    }

    public void setChangeset(BigInteger changeset) {
        this.changeset = changeset;
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

    public void setTags(List<WayTagDao> tags) {
        this.tags = tags;
    }

    public List<WayTagDao> getTags() {
        return tags;
    }

    public void setNdDaos(List<NdDao> ndDaos) {
        this.ndDaos = ndDaos;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Boolean getVisible() {
        return visible;
    }

    public BigInteger getChangeset() {
        return changeset;
    }

    public String getUser() {
        return user;
    }

    public BigInteger getUid() {
        return uid;
    }

    public BigInteger getId() {
        return id;
    }
}
