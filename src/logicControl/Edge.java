package logicControl;

/**
 * Edge class
 * */

public class Edge implements api.EdgeData {
    /**
     * src -> source node of the edge
     * dest -> destination node of the edge
     * w -> the weight of the edge (e.g. length)
     * tag -> for the gui
     * */
    private int src;
    private int dest;
    private double w; ///Weight
    private int tag;
    private String info;

    //constructors
    public Edge(int src, int dest, double w) {
        this.src = src;
        this.dest = dest;
        this.w = w;
        this.info = "";
    }

    public Edge(Edge e){
        this.dest = e.dest;
        this.tag = e.tag;
        this.src = e.src;
        this.w = e.w;
    }

    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.w;
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
                "\"src\": " + src + ",\n" +
                "\"w\": " + w + ",\n" +
                "\"dest\": " + dest + "\n" +
                "}";
    }
}
