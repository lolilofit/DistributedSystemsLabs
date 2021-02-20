package usova.db.dao;

import usova.generated.Member;
import usova.generated.Relation;
import usova.generated.Tag;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RelationDao {
    private List<MemberDao> member;
    
    private List<TagDao> tag;
    
    private BigInteger id;
    
    private String user;
    
    private BigInteger uid;
    
    private Boolean visible;
    
    private BigInteger version;
    
    private BigInteger changeset;

    private Timestamp timestamp;

    private BigInteger osmId;

    public RelationDao(Relation relation, BigInteger osmId) {
        this.id = relation.getId();
        this.timestamp = new Timestamp(relation.getTimestamp().getMillisecond());
        this.changeset = relation.getChangeset();
        this.version = relation.getVersion();
        this.visible = relation.isVisible();
        this.uid = relation.getUid();
        this.user= relation.getUser();
        this.osmId = osmId;

        this.tag = new ArrayList<>();
        if(relation.getTag() != null)
            relation.getTag().forEach(t -> tag.add(new TagDao(t, id)));

        this.member = new ArrayList<>();
        if(relation.getMember() != null)
            relation.getMember().forEach(m -> member.add(new MemberDao(m, id)));
    }

    public BigInteger getOsmId() {
        return osmId;
    }

    public BigInteger getId() {
        return id;
    }

    public BigInteger getUid() {
        return uid;
    }

    public String getUser() {
        return user;
    }

    public List<MemberDao> getMember() {
        return member;
    }

    public BigInteger getChangeset() {
        return changeset;
    }

    public Boolean getVisible() {
        return visible;
    }

    public BigInteger getVersion() {
        return version;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public List<TagDao> getTag() {
        return tag;
    }
}
