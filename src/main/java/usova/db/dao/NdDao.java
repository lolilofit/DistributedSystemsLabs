package usova.db.dao;

import usova.generated.Nd;

import java.math.BigInteger;

public class NdDao {
    private BigInteger ref;

    private BigInteger way;

    public NdDao(Nd nd, BigInteger way) {
        this.ref = nd.getRef();
        this.way = way;
    }

    public BigInteger getRef() {
        return ref;
    }

    public BigInteger getWay() {
        return way;
    }
}
