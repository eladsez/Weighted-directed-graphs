package classes;

import api.EdgeData;
import api.NodeData;
import java.util.HashMap;
import java.util.Iterator;

public class DirectedWeightedGraph implements api.DirectedWeightedGraph {

    private HashMap<Integer, NodeData> Nodes;
    private HashMap<Integer[], EdgeData> Edges;


    @Override
    public NodeData getNode(int key) {
        return this.Nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return this.Edges.get(new Integer[]{src,dest});
    }

    @Override
    public void addNode(NodeData n) {
        this.Nodes.put(n.getKey(),n);
    }

    @Override
    public void connect(int src, int dest, double w) {
        EdgeData currEdge = new classes.EdgeData(src, dest, w);
        this.Edges.put(new Integer[]{src,dest}, currEdge);
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
        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        return null;
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
        return 0;
    }
}
