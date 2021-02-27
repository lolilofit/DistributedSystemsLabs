package usova.db.dao;

import usova.generated.Relation;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RELATION")
public class RelationDao {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "relation", cascade = CascadeType.ALL)
    private List<MemberDao> member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "relationDao", cascade = CascadeType.ALL)
    private List<RelationTagDao> tag;

    @Id
    @Column(name = "id")
    private BigInteger id;

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

    public RelationDao(Relation relation) {
        this.id = relation.getId();
        this.timestamp = new Timestamp(relation.getTimestamp().getMillisecond());
        this.changeset = relation.getChangeset();
        this.version = relation.getVersion();
        this.visible = relation.isVisible();
        this.uid = relation.getUid();
        this.user= relation.getUser();

        this.tag = new ArrayList<>();
        if(relation.getTag() != null)
            relation.getTag().forEach(t -> this.tag.add(new RelationTagDao(t, this)));

        this.member = new ArrayList<>();
        if(relation.getMember() != null)
            relation.getMember().forEach(r -> this.member.add(new MemberDao(r, this)));
    }

    public RelationDao() {}

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

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setChangeset(BigInteger changeset) {
        this.changeset = changeset;
    }

    public void setUid(BigInteger uid) {
        this.uid = uid;
    }

    public void setTag(List<RelationTagDao> tag) {
        this.tag = tag;
    }

    public List<RelationTagDao> getTag() {
        return tag;
    }

    public void setMember(List<MemberDao> member) {
        this.member = member;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }
}
