package logicControl;

import api.DirectedWeightedGraph;
import api.NodeData;

import java.util.*;

public class DirectedWeightedGraphAlgorithms implements api.DirectedWeightedGraphAlgorithms {

    private DirectedWeightedGraph graph;
    private int index;
    int counter = 0;

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
        int i = tarjan().size();
        return i == 1;
    }

    private HashMap tarjan(){
        HashMap<Integer,HashMap<Integer, logicControl.NodeData>> components = new HashMap<>();
        this.index = 0;
        Stack<logicControl.NodeData> stack = new Stack<>();
        Iterator nodeIter = this.graph.nodeIter();
        logicControl.NodeData currNode;
        while (nodeIter.hasNext()){
            currNode = (logicControl.NodeData) nodeIter.next();
            if(currNode.getIndex() == -1)// currNode hasn't been visited
                strongConnect(currNode, stack, components);
        }
        return components;
    }

    private void strongConnect(logicControl.NodeData node, Stack<logicControl.NodeData> stack, HashMap components){
        node.setIndex(this.index);
        node.setLowLink(this.index);
        this.index++;
        stack.push(node);
        node.setOnStack(true);
        Iterator adjIter = graph.edgeIter(node.getKey());
        logicControl.NodeData adjNode = null;
        while (adjIter.hasNext()){
            adjNode = (logicControl.NodeData) graph.getNode(((EdgeData)adjIter.next()).getDest());
            if (adjNode.getIndex() == -1) {
                strongConnect(adjNode, stack, components);
                node.setLowLink(Math.min(node.getLowLink(), adjNode.getLowLink()));
            }
            else if (adjNode.isOnStack())
                node.setLowLink(Math.min(node.getLowLink(), adjNode.getIndex()));
        }

        if (node.getLowLink() == node.getIndex()){
            HashMap<Integer, logicControl.NodeData> component = new HashMap<>();
            while (adjNode != node){
                adjNode = stack.pop();
                adjNode.setOnStack(false);
                component.put(adjNode.getKey(), adjNode);
            }
            this.counter++;
            component.put(node.getKey(), node);
            components.put(node.getKey(), component);
        }
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
