package usova.db.dao;

import usova.generated.Way;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class WayDao {
    private BigInteger id;

    private String user;

    private BigInteger uid;

    private Boolean visible;

    private BigInteger changeset;

    private Timestamp timestamp;

    private List<NdDao> ndDaos;

    private List<TagDao> tags;

    private BigInteger osmId;

    public WayDao(Way way, BigInteger osmId) {
        this.id = way.getId();
        this.user = way.getUser();
        this.uid = way.getUid();
        this.visible = way.isVisible();
        this.changeset = way.getChangeset();
        this.timestamp = new Timestamp(way.getTimestamp().getMillisecond());
        this.osmId = osmId;

        this.ndDaos = new ArrayList<>();
        if (way.getNd() != null)
            way.getNd().forEach(nd -> ndDaos.add(new NdDao(nd, id)));

        this.tags = new ArrayList<>();
      //  if (way.getTag() != null)
      //      way.getTag().forEach(tag -> tags.add(new TagDao(tag, id)));
    }

    public List<NdDao> getNdDaos() {
        return ndDaos;
    }

    public BigInteger getOsmId() {
        return osmId;
    }

    public List<TagDao> getTags() {
        return tags;
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
