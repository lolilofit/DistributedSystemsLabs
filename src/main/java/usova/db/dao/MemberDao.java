package usova.db.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import usova.generated.Member;
import usova.generated.Relation;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "MEMBER")
public class MemberDao {
    @JsonIgnore
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column(name = "type")
    private String type;

    @Column(name = "ref")
    private BigInteger ref;

    @Column(name = "role")
    private String role;

    @JsonIgnore
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "relation")
    private RelationDao relation;

    public MemberDao(Member member, RelationDao relation) {
        this.type = member.getType();
        this.ref = member.getRef();
        this.role = member.getRole();
        this.relation = relation;
    }

    public MemberDao() {}

    public BigInteger getRef() {
        return ref;
    }

    public String getRole() {
        return role;
    }

    public String getType() {
        return type;
    }

    public void setRelation(RelationDao relation) {
        this.relation = relation;
    }

    public RelationDao getRelation() {
        return relation;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setRef(BigInteger ref) {
        this.ref = ref;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setType(String type) {
        this.type = type;
    }
}
