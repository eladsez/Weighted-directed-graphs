package logicControl;

import api.DirectedWeightedGraph;
import api.EdgeData;
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
        while (iter.hasNext()) {
            currNode = new Node((Node) iter.next());
            returnGraph.addNode(currNode);
        }
        iter = this.graph.edgeIter();
        Edge currEdge;
        while (iter.hasNext()) {
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

    private HashMap tarjan() {
        HashMap<Integer, HashMap<Integer, Node>> components = new HashMap<>();
        this.time = 0;
        ((DWGraph) this.graph).resetRevealTime();
        Stack<Node> stack = new Stack<>();
        Iterator nodeIter = this.graph.nodeIter();
        Node currNode;
        while (nodeIter.hasNext()) {
            currNode = (Node) nodeIter.next();
            if (currNode.getRevealTime() == -1)// currNode hasn't been visited
                strongConnect(currNode, stack, components);
        }
        return components;
    }

    private void strongConnect(Node node, Stack<Node> stack, HashMap components) {
        node.setRevealTime(this.time);
        node.setLowLink(this.time);
        this.time++;
        stack.push(node);
        node.setOnStack(true);
        Iterator adjIter = graph.edgeIter(node.getKey());
        Node adjNode = null;
        while (adjIter.hasNext()) {
            adjNode = (Node) graph.getNode(((Edge) adjIter.next()).getDest());
            if (adjNode.getRevealTime() == -1) {
                strongConnect(adjNode, stack, components);
                node.setLowLink(Math.min(node.getLowLink(), adjNode.getLowLink()));
            } else if (adjNode.isOnStack())
                node.setLowLink(Math.min(node.getLowLink(), adjNode.getRevealTime()));
        }

        if (node.getLowLink() == node.getRevealTime()) {
            HashMap<Integer, Node> component = new HashMap<>();
            while (adjNode != node) {
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
        HashMap<Integer, Double> dist = new HashMap<>();
//        HashMap<Integer, Boolean> haveSeen = new HashMap(); TODO: optimization attempt to reduce the scope of the iterator
        Iterator nodeIter = this.graph.nodeIter();
        Node curr;
        while (nodeIter.hasNext()) {
            curr = (Node) nodeIter.next();
            if (curr.getKey() == src)
                dist.put(curr.getKey(), 0.0);
            else
                dist.put(curr.getKey(), Double.MAX_VALUE);
        }
        nodeIter = this.graph.nodeIter();
        Iterator adjIter;
        Node adjNode;
        Edge adjEdge;
        while (nodeIter.hasNext()) {
            curr = (Node) nodeIter.next();
            if (!((DWGraph) this.graph).hasAdj(curr.getKey()))
                continue;
            adjIter = this.graph.edgeIter(curr.getKey());
            while (adjIter.hasNext()) {
                adjEdge = (Edge) adjIter.next();
                adjNode = (Node) this.graph.getNode(adjEdge.getDest());
                dist.put(adjNode.getKey(), Math.min(dist.get(adjNode.getKey()), dist.get(curr.getKey()) + adjEdge.getWeight()));
            }
        }
        return dist.get(dest);
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        HashMap<Integer, Double> dist = new HashMap<>();
        HashMap<Integer, Node> prev = new HashMap<>();
        Iterator nodeIter = this.graph.nodeIter();
        Node curr;
        while (nodeIter.hasNext()) {
            curr = (Node) nodeIter.next();
            if (curr.getKey() == src)
                dist.put(curr.getKey(), 0.0);
            else
                dist.put(curr.getKey(), Double.MAX_VALUE);
        }
        nodeIter = this.graph.nodeIter();
        Iterator adjIter;
        Node adjNode;
        Edge adjEdge;
        double sumOfDist;
        while (nodeIter.hasNext()) {
            curr = (Node) nodeIter.next();
            if (!((DWGraph) this.graph).hasAdj(curr.getKey()))
                continue;
            adjIter = this.graph.edgeIter(curr.getKey());
            while (adjIter.hasNext()) {
                adjEdge = (Edge) adjIter.next();
                adjNode = (Node) this.graph.getNode(adjEdge.getDest());
                sumOfDist = dist.get(curr.getKey()) + adjEdge.getWeight();
                if (sumOfDist < dist.get(adjNode.getKey())) {
                    dist.put(adjNode.getKey(), sumOfDist);
                    prev.put(adjNode.getKey(), curr);
                }
            }
        }
        List returnList = new Vector();
        Node minPath = (Node) this.graph.getNode(dest);
        while (minPath != this.graph.getNode(src)) {
            returnList.add(minPath);
            minPath = prev.get(minPath.getKey());
        }
        returnList.add(this.graph.getNode(src));
        Collections.reverse(returnList);
        return returnList;
    }

        @Override
    public NodeData center () {
        Node center = null;
        double minDist = Double.MAX_VALUE;
        double currDist;
        Iterator nodeIter = this.graph.nodeIter();
        Node temp;
        while (nodeIter.hasNext()){
            temp = (Node)nodeIter.next();
            currDist = farestDist(temp.getKey());
            if (minDist > currDist) {
                minDist = currDist;
                center = temp;
            }
        }
        return center;
    }

    private double farestDist(int src){
        Iterator iter = this.graph.nodeIter();
        Node checkNode;
        double returnDist = -1;
        double currDist;
        while (iter.hasNext()){
            checkNode = (Node) iter.next();
            if (checkNode.getKey() == src)
                continue;
            currDist = shortestPathDist(src, checkNode.getKey());
            if (returnDist < currDist)
                returnDist = currDist;
        }
        return returnDist;
    }

    @Override
    public List<NodeData> tsp (List < NodeData > cities) {
        return null;
    }

    @Override
    public boolean save (String file){
        return false;
    }

    @Override
    public boolean load (String file){
        return false;
    }
}
