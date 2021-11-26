package classes;

import java.awt.*;

public class NodeData implements api.NodeData {

    private int id;
    private GeoLocation pos;
    private double weight;
    private int tag;

    public NodeData(int id, int tag) {
        this.id = id;
        this.tag = tag;
    }

    @Override
    public int getKey() {
        return this.id;
    }

    @Override
    public api.GeoLocation getPos() {
        return this.pos;
    }

    @Override
    public void setPos(api.GeoLocation p) {
        this.pos = new GeoLocation(p.x(), p.y(), p.z());
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return null; ////// complete
    }

    @Override
    public void setInfo(String s) {
        ////// complete
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    @Override
    public String toString() {
        return "NodeData{" +
                "id=" + id +
                ", pos=" + pos +
                ", weight=" + weight +
                ", tag=" + tag +
                '}';
    }
}
