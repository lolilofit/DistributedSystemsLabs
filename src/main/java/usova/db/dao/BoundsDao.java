package usova.db.dao;

import usova.generated.Bounds;

import java.math.BigInteger;

public class BoundsDao {
    private BigInteger id;

    private Double minlat;

    private Double minlon;

    private Double maxlat;

    private Double maxlon;

    public BoundsDao(Bounds bounds, BigInteger id) {
        this.minlat = bounds.getMinlat();
        this.minlon = bounds.getMinlon();
        this.maxlat = bounds.getMaxlat();
        this.maxlon = bounds.getMaxlon();
        this.id = id;
    }

    public Double getMaxlat() {
        return maxlat;
    }

    public Double getMaxlon() {
        return maxlon;
    }

    public Double getMinlat() {
        return minlat;
    }

    public Double getMinlon() {
        return minlon;
    }
}
