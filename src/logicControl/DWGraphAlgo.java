package logicControl;

import api.DirectedWeightedGraph;
import api.NodeData;

import java.util.*;

public class DWGraphAlgo implements api.DirectedWeightedGraphAlgorithms {

    private DirectedWeightedGraph graph;
    private int time; //Discovery time for dfs

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
        DWGraph returnGraph = new DWGraph();
        Iterator iter = this.graph.nodeIter();
        NodeData currNode;
        while (iter.hasNext()){
            currNode = new Node((Node) iter.next());
            returnGraph.addNode(currNode);
        }
        iter = this.graph.edgeIter();
        Edge currEdge;
        while (iter.hasNext()){
            currEdge = (Edge) iter.next();
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
        HashMap<Integer,HashMap<Integer, Node>> components = new HashMap<>();
        this.time = 0;
        ((DWGraph)this.graph).resetRevealTime();
        Stack<Node> stack = new Stack<>();
        Iterator nodeIter = this.graph.nodeIter();
        Node currNode;
        while (nodeIter.hasNext()){
            currNode = (Node) nodeIter.next();
            if(currNode.getRevealTime() == -1)// currNode hasn't been visited
                strongConnect(currNode, stack, components);
        }
        return components;
    }

    private void strongConnect(Node node, Stack<Node> stack, HashMap components){
        node.setRevealTime(this.time);
        node.setLowLink(this.time);
        this.time++;
        stack.push(node);
        node.setOnStack(true);
        Iterator adjIter = graph.edgeIter(node.getKey());
        Node adjNode = null;
        while (adjIter.hasNext()){
            adjNode = (Node) graph.getNode(((Edge)adjIter.next()).getDest());
            if (adjNode.getRevealTime() == -1) {
                strongConnect(adjNode, stack, components);
                node.setLowLink(Math.min(node.getLowLink(), adjNode.getLowLink()));
            }
            else if (adjNode.isOnStack())
                node.setLowLink(Math.min(node.getLowLink(), adjNode.getRevealTime()));
        }

        if (node.getLowLink() == node.getRevealTime()){
            HashMap<Integer, Node> component = new HashMap<>();
            while (adjNode != node){
                adjNode = stack.pop();
                adjNode.setOnStack(false);
                component.put(adjNode.getKey(), adjNode);
            }
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
