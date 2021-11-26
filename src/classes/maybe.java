package classes;

import api.EdgeData;
import api.NodeData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class maybe implements api.DirectedWeightedGraph {

    private HashMap<Integer, NodeData> Nodes;
    private HashMap<ArrayList<Integer>, EdgeData> Edges;
    private int mc; //counting changes in the graph


    @Override
    public NodeData getNode(int key) {
        return this.Nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        ArrayList<Integer> key = new ArrayList<Integer>();
        key.add(src);
        key.add(dest);
        return this.Edges.get(key);
    }

    @Override
    public void addNode(NodeData n) {
        this.Nodes.put(n.getKey(),n);
    }

    @Override
    public void connect(int src, int dest, double w) {
        if (this.Nodes.containsKey(src) && this.Nodes.containsKey(dest)) {
            EdgeData currEdge = new classes.EdgeData(src, dest, w);
            ArrayList<Integer> key = new ArrayList<Integer>();
            key.add(src);
            key.add(dest);
            this.Edges.put(key, currEdge);
        }
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        return this.Nodes.remove(key);
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        return this.Edges.remove(new Integer[]{src, dest});
    }

    @Override
    public int nodeSize() {
        return this.Nodes.size();
    }

    @Override
    public int edgeSize() {
        return this.Edges.size();
    }

    @Override
    public int getMC() {
        return this.mc;
    }

    public static void main(String[] args) {
        HashMap<ArrayList<Integer>, EdgeData> Edges = new HashMap<>();
        EdgeData currEdge = new classes.EdgeData(2, 5, 33.0);
        ArrayList<Integer> key1 = new ArrayList<Integer>();
        key1.add(2);
        key1.add(5);
        Edges.put(key1, currEdge);
        ArrayList<Integer> key2 = new ArrayList<Integer>();
        key2.add(2);
        key2.add(5);
        EdgeData e = Edges.get(key2);
        System.out.println(e.getWeight());

    }
}
