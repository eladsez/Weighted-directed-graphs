package logicControl;

public class Edge implements api.EdgeData {

    private int src;
    private int dest;
    private double w; ///Weight
    private int tag;

    public Edge(int src, int dest, double w) {
        this.src = src;
        this.dest = dest;
        this.w = w;
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
        return null;////// complete
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
        return "{\n" +
                "\"src\": " + src + ",\n" +
                "\"w\": " + w + ",\n" +
                "\"dest\": " + dest + "\n" +
                "}";
    }
}
