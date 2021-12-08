package logicControl;

/**
 * Node class
 * */

public class Node implements api.NodeData {
    /**
     * id -> id
     * pos -> geolocation position
     * weight -> the weight of the node - not relevant for this assignment
     * tag -> tag for the gui
     * lowLink -> for the connected algorithm
     * revealTime -> for the dfs algorithm
     * onStack -> a boolean that say if a node is in the stack - for the connected algorithm
     * */
    private int id;
    private api.GeoLocation pos;
    private double weight;
    private int tag;
    private String info;


    //constructors
    public Node(int id, api.GeoLocation pos) {
        this.id = id;
        this.pos = pos;
    }

    //deep copy constructor
    public Node(Node n){
        this.id = n.id;
        this.pos = n.pos;
        this.tag = n.tag;
        this.weight = n.weight;
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
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
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
        return "{\n" +
                "\"pos\": " + pos + ",\n" +
                "\"id\": " + id + "\n" +
                "}";
    }
}
