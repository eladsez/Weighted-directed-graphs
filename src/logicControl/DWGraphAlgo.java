package logicControl;

import api.DirectedWeightedGraph;
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
    private DirectedWeightedGraph graph;
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
        } else this.graph = g;
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
        int i = tarjan().size();
        return i == 1;
    }

    /**
     * the algorithm we used in the isConncted function
     * this is the driving function, and the heavy lifting is done with the strong Connect function
     */
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

    /**
     * this is the heavy function of the isConnect function
     */
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
            if (!((DWGraph) this.graph).hasAdj(adjNode.getKey()))
                continue;
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


    /**
     * Finds the NodeData which minimizes the max distance to all the other nodes.
     * Assuming the graph isConnected, elese return null. See: https://en.wikipedia.org/wiki/Graph_center
     *
     * @return the Node data to which the max shortest path to all the other nodes is minimized.
     */
    @Override
    public NodeData center() {
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
        double returnDist = -1;
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
            ((DWGraph) this.graph).initFromFile(file);
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
