package usova.db.dao;

import usova.generated.Osm;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class OsmDao {
    private BoundsDao bounds;

    private List<NodeDao> node;

    private List<WayDao> way;

    private List<RelationDao> relation;

    private Float version;

    private String generator;

    private BigInteger id;

    public OsmDao(Osm osm, BigInteger boundsId, BigInteger id) {
        this.version = osm.getVersion();
        this.generator = osm.getGenerator();
        this.bounds = new BoundsDao(osm.getBounds(), boundsId);
        this.id = id;

        this.relation = new ArrayList<>();
        this.node = new ArrayList<>();
        this.way = new ArrayList<>();
    }

    public BigInteger getId() {
        return id;
    }

    public BoundsDao getBounds() {
        return bounds;
    }

    public Float getVersion() {
        return version;
    }

    public List<NodeDao> getNode() {
        return node;
    }

    public List<RelationDao> getRelation() {
        return relation;
    }

    public List<WayDao> getWay() {
        return way;
    }

    public String getGenerator() {
        return generator;
    }
    
}
