package classes;
import api.EdgeData;
import api.GeoLocation;
import api.NodeData;
import classes.graphIterators.EdgesIterator;
import classes.graphIterators.NodeIterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This is a java class containing methods of a DirectedWeightedGraph.
 * (according to the interface we were given).
 */

public class DirectedWeightedGraph implements api.DirectedWeightedGraph {
    private HashMap<Integer, NodeData> Nodes;
    private HashMap<Integer, HashMap<Integer, EdgeData>> Edges; // hash map of hashmaps > get.get in o(1)
    //<src,<dest,edge>>
    private int MC; // every time our graph changes, increase this.
    private int edgeSize;

    public DirectedWeightedGraph() {
        this.Nodes = new HashMap<>();
        this.Edges = new HashMap<>();
        this.MC = 0;
        this.edgeSize = 0;
    }

    @Override
    public NodeData getNode(int key) {
        return this.Nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return this.Edges.get(src).get(dest);

    }
    @Override
    public void addNode(NodeData n) {
        this.Nodes.put(n.getKey(), n);
        this.MC++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        EdgeData newEdge = new classes.EdgeData(src,dest,w);
        if (this.Edges.containsKey(src))//checks if already exist hashmap for the edges coming out from this node
            //append newEdge into the above hashmap
            this.Edges.get(src).put(dest,newEdge);
        else {
            HashMap<Integer, EdgeData> tmp = new HashMap<>(); // creates a new edge Hashmap for this src.
            tmp.put(dest,newEdge);
            this.Edges.put(src,tmp);
        }
        this.MC++;
        this.edgeSize++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return (Iterator) new NodeIterator(this);
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return (Iterator)new EdgesIterator(this);
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        //converts the edges hashmap that going out from node_id into Collection
        Collection<EdgeData> tempColl = this.Edges.get(node_id).values();
        Iterator<EdgeData> iter = tempColl.iterator();//create Iterator for the above collection
        return iter;
    }

    @Override
    public NodeData removeNode(int key) {
        this.edgeSize -= this.Edges.get(key).size(); // decrease the amount of edges going out from this node(key)
        this.Edges.remove(key); // remove the edges going out from this node(key)
        //remove the edges going into this node(key)
        this.Edges.forEach((src, HashMap) -> {
            if(HashMap.containsKey(key))
                this.edgeSize--;
                HashMap.remove(key);
        });
        this.MC++; // increase changes to graph
        return this.Nodes.remove(key); // simply remove the node
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        this.MC++; // increase changes to graph
        this.edgeSize--;
        return this.Edges.get(src).remove(dest); // returns and remove the EDGE (null if none)
    }

    @Override
    public int nodeSize() {
        return this.Nodes.size();
    }

    @Override
    public int edgeSize() {
        return this.edgeSize;
    }

    @Override
    public int getMC() {
        return this.MC;
    }

    public Collection<NodeData> getNodeColl(){
        return Nodes.values();
    }

    public Collection<EdgeData> getEdgeColl(){
        List<EdgeData> list = new ArrayList<>();
        this.Edges.forEach((src, HashMap) -> {
            Object[] tempO = HashMap.values().toArray();
            EdgeData[] temp = new EdgeData[tempO.length];
            for (int i = 0; i < tempO.length; i++)
                temp[i] = (EdgeData)tempO[i];
            Collections.addAll(list, temp);
        });
        return list;
    }

    public void initFromFile(String filePath) throws IOException, ParseException {
        // parsing file "G1.json"
        Object obj = new JSONParser().parse(new FileReader(filePath));

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        JSONArray ja = (JSONArray) jo.get("Edges"); // getting the edges
        Iterator iter = ja.iterator();// iterating the edges
        while (iter.hasNext()) {// adding the edges into the graph
            Map edgeMap = (Map) iter.next();
            this.connect((int)(long)edgeMap.get("src"),
                    (int)(long)edgeMap.get("dest"),(double)edgeMap.get("w"));
        }

        ja = (JSONArray) jo.get("Nodes");// getting the nodes
        iter = ja.iterator();// iterating the nodes
        while (iter.hasNext()) { // adding the nodes into the graph
            Map nodeMap = (Map) iter.next();
            String[] sGeo = ((String)nodeMap.get("pos")).split(",");
            GeoLocation geo = new classes.GeoLocation(Double.parseDouble(sGeo[0]), Double.parseDouble(sGeo[1])
                    , Double.parseDouble(sGeo[2]));
            NodeData currNode = new classes.NodeData((int)(long)nodeMap.get("id"), geo);
            this.addNode(currNode);
        }

    }
}