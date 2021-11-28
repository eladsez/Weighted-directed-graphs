package logicControl;

import api.DirectedWeightedGraph;
import api.NodeData;

import java.util.Iterator;
import java.util.List;

public class DirectedWeightedGraphAlgorithms implements api.DirectedWeightedGraphAlgorithms {

    private DirectedWeightedGraph graph;

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        logicControl.DirectedWeightedGraph returnGraph = new logicControl.DirectedWeightedGraph();
        Iterator iter = this.graph.nodeIter();
        NodeData currNode;
        while (iter.hasNext()){
            currNode = new logicControl.NodeData((logicControl.NodeData) iter.next());
            returnGraph.addNode(currNode);
        }
        iter = this.graph.edgeIter();
        EdgeData currEdge;
        while (iter.hasNext()){
            currEdge = (EdgeData) iter.next();
            returnGraph.connect(currEdge.getSrc(), currEdge.getDest(), currEdge.getWeight());
        }

        return returnGraph;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
