package logicControl;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * This class represents a Directed (positive) Weighted Graph Theory Algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected(); // strongly (all ordered pais connected)
 * 3. double shortestPathDist(int src, int dest);
 * 4. List<NodeData> shortestPath(int src, int dest);
 * 5. NodeData center(); // finds the NodeData which minimizes the max distance to all the other nodes.
 * // Assuming the graph isConnected, elese return null.
 * 6. List<NodeData> tsp(List<NodeData> cities); // computes a list of consecutive nodes which go over all the nodes in cities.
 * <p>
 * 7. save(file); // JSON file
 * 8. load(file); // JSON file
 */

public class DWGraphAlgo implements api.DirectedWeightedGraphAlgorithms {
    /**
     * graph -> the graph
     * time -> Discovery time for dfs
     */
    private DWGraph graph;
    private int time;

    public DWGraphAlgo() {
        this.graph = new DWGraph();
    }

    /**
     * Inits the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(DirectedWeightedGraph g) {
        if (g == null) {
            this.graph = new DWGraph();
        } else this.graph = (DWGraph) g;
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


    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        DWGraph trans = (DWGraph) transpose(this.graph);
        // TODO check if there is another src to give to the bfs
        boolean ans = bfs(this.graph, 0) == this.graph.nodeSize() && this.graph.nodeSize() == bfs(trans, 0);
        this.graph.resetTag();
        return ans;
    }

     private DirectedWeightedGraph transpose(DirectedWeightedGraph graph){
        DirectedWeightedGraph returnG = new DWGraph();
        Iterator iter = graph.nodeIter();
        while (iter.hasNext())
            returnG.addNode(new Node((Node) iter.next()));

        iter = graph.edgeIter();
        Edge currEdge;
        while (iter.hasNext()){
            currEdge = (Edge) iter.next();
            returnG.connect(currEdge.getDest(), currEdge.getSrc(), currEdge.getWeight());
        }
        return returnG;
    }


    /// 0 - unvisited ,  1 - in progress,  2 - visited
    private static int bfs(DWGraph graph, int src) {
        int nodeCounter = 1; // set to 1 not to 0 because we already count the src
        Queue<Node> qu = new LinkedList<>();
        Iterator iter = graph.nodeIter();
        while (iter.hasNext()) {
            ((Node) iter.next()).setTag(0);
        }
        graph.getNode(src).setTag(1);
        qu.add((Node) graph.getNode(src));
        Node currNode, adjNode;
        while (!qu.isEmpty()) {
            currNode = qu.poll();
            if (!graph.hasAdj(currNode.getKey())) {
                currNode.setTag(2);
                continue;
            }
            iter = graph.edgeIter(currNode.getKey());
            while (iter.hasNext()) {
                adjNode = (Node) graph.getNode(((Edge) iter.next()).getDest());
                if (adjNode.getTag() == 0) {
                    nodeCounter++;
                    adjNode.setTag(1);
                    qu.add(adjNode);
                }
            }
            currNode.setTag(2);
        }
        return nodeCounter;
    }

    private void dijkstra(int src) {
        Iterator Iter = this.graph.nodeIter();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(NodeData::getWeight));//priority queue sorting by the d(node)
        Node curr;
        while (Iter.hasNext()) {// initial all the d(node) to infinite and d(srcNode) = 0
            curr = (Node) Iter.next();
            if (curr.getKey() == src)
                curr.setWeight(0);
            else
                curr.setWeight(Double.MAX_VALUE);
            pq.add(curr); // adding the node to the priority queue
        }
        Node adjNode;
        Edge connEdge;

        while (!pq.isEmpty()) {
            curr = pq.poll(); // poll the node with minimum d(node)
            if (!this.graph.hasAdj(curr.getKey()))// checking if curr have any edges coming out of him
                continue;

            Iter = this.graph.edgeIter(curr.getKey());
            while (Iter.hasNext()) { // for each edge leaving curr
                connEdge = (Edge) Iter.next();
                adjNode = (Node) this.graph.getNode(connEdge.getDest());
                if (adjNode.getWeight() > curr.getWeight() + connEdge.getWeight()) {
                    adjNode.setWeight(curr.getWeight() + connEdge.getWeight());
                    adjNode.setTag(curr.getKey());
                    pq.remove(adjNode);
                    pq.add(adjNode);
                }
            }
        }
    }

    /**
     * Computes the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * we used the dijkstra algorithm
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return the shortest dist
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (!this.graph.containNode(src) || !this.graph.containNode(dest))
            return -1;

        dijkstra(src);
        double ans = this.graph.getNode(dest).getWeight();
        this.graph.resetTag();
        this.graph.resetNodeW();
        if (ans == Double.MAX_VALUE)
            return -1;
        return ans;
    }


    /**
     * Computes the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * Note if no such path --> returns null;
     * we used the dijkstra algorithm
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        if (!this.graph.containNode(src) || !this.graph.containNode(dest))
            return null;
        dijkstra(src);
        Node curr;
        List returnList = new Vector<Node>();// list to collect the path
        curr = (Node) this.graph.getNode(dest);
        while (curr != this.graph.getNode(src)) {
            returnList.add(curr);
            curr = (Node) this.graph.getNode(curr.getTag());
        }
        returnList.add(this.graph.getNode(src));
        Collections.reverse(returnList);
        this.graph.resetNodeW(); // reset the nodes weight to 0
        this.graph.resetTag();
        if (returnList.size() == 0)
            return null;
        return returnList;
    }


    /**
     * Finds the NodeData which minimizes the max distance to all the other nodes.
     * Assuming the graph isConnected, elese return null. See: https://en.wikipedia.org/wiki/Graph_center
     *
     * @return the Node data to which the max shortest path to all the other nodes is minimized.
     */
    @Override
    public NodeData center() {
        if (!this.isConnected())
            return null;
        Node center = null;
        double minDist = Double.MAX_VALUE;
        double currDist;
        Iterator nodeIter = this.graph.nodeIter();
        Node temp;
        while (nodeIter.hasNext()) {
            temp = (Node) nodeIter.next();
            currDist = farestDist(temp.getKey());
            if (minDist > currDist) {
                minDist = currDist;
                center = temp;
            }
        }
        return center;
    }

    /**
     * helper function to the center function
     */
    private double farestDist(int src) {
        Iterator iter = this.graph.nodeIter();
        Node checkNode;
        double returnDist = Double.MIN_VALUE;
        double currDist;
        while (iter.hasNext()) {
            checkNode = (Node) iter.next();
            if (checkNode.getKey() == src)
                continue;
            currDist = shortestPathDist(src, checkNode.getKey());
            if (returnDist < currDist)
                returnDist = currDist;
        }
        return returnDist;
    }


    /**
     * Computes a list of consecutive nodes which go over all the nodes in cities.
     * the sum of the weights of all the consecutive (pairs) of nodes (directed) is the "cost" of the solution -
     * the lower the better.
     */
    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }


    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try {
            FileWriter Jfile = new FileWriter(file);
            BufferedWriter buffer = new BufferedWriter(Jfile);
            buffer.write(this.graph.toString());
            buffer.close();
            Jfile.close();
        } catch (IOException e) {
//            e.printStackTrace(); can be added if stackTrace is needed
            return false;
        }
        return true;
    }


    /**
     * This method loads a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            this.graph.initFromFile(file);
        } catch (IOException e) {
//            e.printStackTrace(); can be added if stackTrace is needed
            return false;
        } catch (ParseException e) {
//            e.printStackTrace(); can be added if stackTrace is needed
            return false;
        }
        return true;
    }
}
