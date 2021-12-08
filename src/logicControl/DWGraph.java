package logicControl;

import api.*;
import api.GeoLocation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/**
 * This is a java class containing methods of a DirectedWeightedGraph.
 * (according to the interface we were given).
 */

public class DWGraph implements DirectedWeightedGraph {
    /**
     * Nodes -> HashMap the contains all the nodes of the graph
     * Edges -> HashMap the contains all the edges of the graph, each edge is represented with a hashMap
     * MC -> keeps track of the changes that was done on the graph
     * edgeSize -> keep track on the size of the graph
     */
    private HashMap<Integer, NodeData> Nodes;
    private HashMap<Integer, HashMap<Integer, EdgeData>> Edges; // hash map of hashmaps > get.get in o(1)
    //<src,<dest,edge>>
    private int MC; // every time our graph changes, increase this.
    private int edgeSize;

    //constructor
    public DWGraph() {
        this.Nodes = new HashMap<>();
        this.Edges = new HashMap<>();
        this.MC = 0;
        this.edgeSize = 0;
    }

    //checks if Edeges contains a node (if there are any edges coming out of the node)
    public boolean hasAdj(int src) { // checks if the src node has any edges coming out of him
        return this.Edges.containsKey(src);
    }

    @Override
    public NodeData getNode(int key) {
        return this.Nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return this.Edges.get(src).get(dest);

    }

    public boolean containNode(int key){
        return this.Nodes.containsKey(key);
    }

    @Override
    public void addNode(NodeData n) {
        this.Nodes.put(n.getKey(), n);
        this.MC++;
    }


    /**
     * Connects an edge with weight w between node src to node dest.
     * * Note: this method should run in O(1) time.
     *
     * @param src  - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        EdgeData newEdge = new Edge(src, dest, w);
        if (this.Edges.containsKey(src))//checks if already exist hashmap for the edges coming out from this node
            //append newEdge into the above hashmap
            this.Edges.get(src).put(dest, newEdge);
        else {
            HashMap<Integer, EdgeData> tmp = new HashMap<>(); // creates a new edge Hashmap for this src.
            tmp.put(dest, newEdge);
            this.Edges.put(src, tmp);
        }
        this.MC++;
        this.edgeSize++;
    }


    /**
     * This method returns an Iterator for the
     * collection representing all the nodes in the graph.
     * Note: if the graph was changed since the iterator was constructed - a RuntimeException should be thrown.
     *
     * @return Iterator<node_data>
     */
    @Override
    public Iterator<NodeData> nodeIter() {
        return new Iterator<NodeData>() {
            private final int MCheck = MC;
            private Iterator<NodeData> iter = Nodes.values().iterator();

            @Override
            public boolean hasNext() {
                if (this.MCheck != MC)
                    throw new RuntimeException("The graph has changed since the iterator was constructed!");
                return iter.hasNext();
            }

            @Override
            public NodeData next() {
                if (this.MCheck != MC)
                    throw new RuntimeException("The graph has changed since the iterator was constructed!");
                return (NodeData) iter.next();
            }
            @Override
            public void remove() {
                if (this.MCheck != MC)
                    throw new RuntimeException("The graph has changed since the iterator was constructed!");
                iter.remove();
            }

            @Override
            public void forEachRemaining(Consumer<? super NodeData> action) {
                if (this.MCheck != MC)
                    throw new RuntimeException("The graph has changed since the iterator was constructed!");
                iter.forEachRemaining(action);
            }
        };
    }


    /**
     * This method returns an Iterator for all the edges in this graph.
     * Note: if any of the edges going out of this node were changed since the iterator was constructed - a RuntimeException should be thrown.
     *
     * @return Iterator<EdgeData>
     */
    @Override
    public Iterator<EdgeData> edgeIter() {
        return new Iterator<EdgeData>() {
            private final int MCheck = MC;
            private Iterator<EdgeData> iter = getEdgeColl().iterator();

            @Override
            public boolean hasNext() {
                if (this.MCheck != MC)
                    throw new RuntimeException("The graph has changed since the iterator was constructed!");
                return iter.hasNext();
            }

            @Override
            public EdgeData next() {
                if (this.MCheck != MC)
                    throw new RuntimeException("The graph has changed since the iterator was constructed!");
                return (EdgeData) iter.next();
            }
            @Override
            public void remove() {
                if (this.MCheck != MC)
                    throw new RuntimeException("The graph has changed since the iterator was constructed!");
                iter.remove();
            }

            @Override
            public void forEachRemaining(Consumer<? super EdgeData> action) {
                if (this.MCheck != MC)
                    throw new RuntimeException("The graph has changed since the iterator was constructed!");
                iter.forEachRemaining(action);
            }
        };
    }


    /**
     * This method returns an Iterator for edges getting out of the given node (all the edges starting (source) at the given node).
     * Note: if the graph was changed since the iterator was constructed - a RuntimeException should be thrown.
     *
     * @return Iterator<EdgeData>
     */
    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return new Iterator<EdgeData>() {
            private final int MCheck = MC;
            private Iterator<EdgeData> iter = Edges.get(node_id).values().iterator();

            @Override
            public boolean hasNext() {
                if (this.MCheck != MC)
                    throw new RuntimeException("The graph has changed since the iterator was constructed!");
                return iter.hasNext();
            }

            @Override
            public EdgeData next() {
                if (this.MCheck != MC)
                    throw new RuntimeException("The graph has changed since the iterator was constructed!");
                return (EdgeData) iter.next();
            }

            @Override
            public void remove() {
                if (this.MCheck != MC)
                    throw new RuntimeException("The graph has changed since the iterator was constructed!");
                iter.remove();
            }

            @Override
            public void forEachRemaining(Consumer<? super EdgeData> action) {
                if (this.MCheck != MC)
                    throw new RuntimeException("The graph has changed since the iterator was constructed!");
                iter.forEachRemaining(action);
            }
        };
    }


    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(k), V.degree=k, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public NodeData removeNode(int key) {
        this.edgeSize -= this.Edges.get(key).size(); // decrease the amount of edges going out from this node(key)
        this.Edges.remove(key); // remove the edges going out from this node(key)
        //remove the edges going into this node(key)
        this.Edges.forEach((src, HashMap) -> {
            if (HashMap.containsKey(key))
                this.edgeSize--;
            HashMap.remove(key);
        });
        this.MC++; // increase changes to graph
        return this.Nodes.remove(key); // simply remove the node
    }


    /**
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public EdgeData removeEdge(int src, int dest) {
        this.MC++; // increase changes to graph
        this.edgeSize--;
        return this.Edges.get(src).remove(dest); // returns and remove the EDGE (null if none)
    }

    /**
     * Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return this.Nodes.size();
    }

    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return this.edgeSize;
    }

    @Override
    public int getMC() {
        return this.MC;
    }

    public Collection<NodeData> getNodeColl() {
        return Nodes.values();
    }


    public Collection<EdgeData> getEdgeColl() {
        List<EdgeData> list = new ArrayList<>();
        this.Edges.forEach((src, HashMap) -> {
            Object[] tempO = HashMap.values().toArray();
            EdgeData[] temp = new EdgeData[tempO.length];
            for (int i = 0; i < tempO.length; i++)
                temp[i] = (EdgeData) tempO[i];
            Collections.addAll(list, temp);
        });
        return list;
    }

    public void resetNodeW(){
        Nodes.forEach((id, node) -> {
            node.setWeight(0);
        });
    }

    public void resetTag(){
        Nodes.forEach((id, node) -> {
            node.setTag(0);
        });
    }

    public static DirectedWeightedGraph transpose(DirectedWeightedGraph graph){
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

    /**
     * this function Loading the graph from a Json file
     *
     * @param filePath -> the path for the Json file
     * @throws IOException
     * @throws ParseException
     */
    public void initFromFile(String filePath) throws IOException, ParseException {
        this.Nodes = new HashMap<>();
        this.Edges = new HashMap<>();
        this.MC = 0;
        this.edgeSize = 0;

        // parsing file "G1.json"
        Object obj = new JSONParser().parse(new FileReader(filePath));

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        JSONArray ja = (JSONArray) jo.get("Edges"); // getting the edges
        Iterator iter = ja.iterator();// iterating the edges
        Map edgeMap;
        while (iter.hasNext()) {// adding the edges into the graph
            edgeMap = (Map) iter.next();
            this.connect((int) (long) edgeMap.get("src"),
                    (int) (long) edgeMap.get("dest"), (double) edgeMap.get("w"));
        }

        ja = (JSONArray) jo.get("Nodes");// getting the nodes
        iter = ja.iterator();// iterating the nodes
        Map nodeMap;
        String[] sGeo;
        GeoLocation geo;
        NodeData currNode;
        while (iter.hasNext()) { // adding the nodes into the graph
            nodeMap = (Map) iter.next();
            sGeo = ((String) nodeMap.get("pos")).split(",");
            geo = new logicControl.GeoLocation(Double.parseDouble(sGeo[0]), Double.parseDouble(sGeo[1])
                    , Double.parseDouble(sGeo[2]));
            currNode = new Node((int) (long) nodeMap.get("id"), geo);
            this.addNode(currNode);
        }

    }

    @Override
    public String toString() {
        Iterator nodeIter = this.nodeIter();
        Iterator edgeIter = this.edgeIter();
        String ans = "{\n" +
                "\"Edges\": [\n";

        while (edgeIter.hasNext()) {
            ans += edgeIter.next().toString();
            if (edgeIter.hasNext())
                ans += ",\n";
            else ans += "\n";
        }

        ans += "],\n" +
                "\"Nodes\": [";

        while (nodeIter.hasNext()) {
            ans += nodeIter.next().toString();
            if (nodeIter.hasNext())
                ans += ",\n";
            else ans += "\n";
        }


        ans += "]\n" +
                '}';
        return ans;
    }
}