package usova.db.dao;

import usova.generated.Member;

import java.math.BigInteger;

public class MemberDao {
    private String type;

    private BigInteger ref;

    private String role;

    private BigInteger relationId;

    public MemberDao(Member member, BigInteger relationId) {
        this.type = member.getType();
        this.ref = member.getRef();
        this.role = member.getRole();
        this.relationId = relationId;
    }

    public BigInteger getRelationId() {
        return relationId;
    }

    public BigInteger getRef() {
        return ref;
    }

    public String getRole() {
        return role;
    }

    public String getType() {
        return type;
    }
}
