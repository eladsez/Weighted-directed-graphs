package logicControl;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.GeoLocation;
import api.NodeData;
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

public class DWGraph implements DirectedWeightedGraph {
    private HashMap<Integer, NodeData> Nodes;
    private HashMap<Integer, HashMap<Integer, EdgeData>> Edges; // hash map of hashmaps > get.get in o(1)
    //<src,<dest,edge>>
    private int MC; // every time our graph changes, increase this.
    private int edgeSize;

    public DWGraph() {
        this.Nodes = new HashMap<>();
        this.Edges = new HashMap<>();
        this.MC = 0;
        this.edgeSize = 0;
    }

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
    @Override
    public void addNode(NodeData n) {
        this.Nodes.put(n.getKey(), n);
        this.MC++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        EdgeData newEdge = new Edge(src,dest,w);
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
        };
    }

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
        };
    }

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
        };
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

    public void resetRevealTime(){
        Nodes.forEach((id, node) -> {
            ((Node)node).setRevealTime(-1);
        });
    }

    /**
     * this function Loading the graph from a Json file
     * @param filePath -> the path for the Json file
     * @throws IOException
     * @throws ParseException
     */
    public void initFromFile(String filePath) throws IOException, ParseException {
        // parsing file "G1.json"
        Object obj = new JSONParser().parse(new FileReader(filePath));

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        JSONArray ja = (JSONArray) jo.get("Edges"); // getting the edges
        Iterator iter = ja.iterator();// iterating the edges
        Map edgeMap;
        while (iter.hasNext()) {// adding the edges into the graph
            edgeMap = (Map) iter.next();
            this.connect((int)(long)edgeMap.get("src"),
                    (int)(long)edgeMap.get("dest"),(double)edgeMap.get("w"));
        }

        ja = (JSONArray) jo.get("Nodes");// getting the nodes
        iter = ja.iterator();// iterating the nodes
        Map nodeMap;
        String[] sGeo;
        GeoLocation geo;
        NodeData currNode;
        while (iter.hasNext()) { // adding the nodes into the graph
            nodeMap = (Map) iter.next();
            sGeo = ((String)nodeMap.get("pos")).split(",");
            geo = new logicControl.GeoLocation(Double.parseDouble(sGeo[0]), Double.parseDouble(sGeo[1])
                    , Double.parseDouble(sGeo[2]));
            currNode = new Node((int)(long)nodeMap.get("id"), geo);
            this.addNode(currNode);
        }

    }

    @Override
    public String toString() {
        Iterator nodeIter = this.nodeIter();
        Iterator edgeIter = this.edgeIter();
        String ans =  "{\n" +
                "\"Edges\": [\n";

        while (edgeIter.hasNext()) {
            ans += edgeIter.next().toString();
            if (edgeIter.hasNext())
                ans += ",\n";
            else ans += "\n";
        }

        ans+=    "],\n" +
                "\"Nodes\": [" ;

        while (nodeIter.hasNext()) {
            ans += nodeIter.next().toString();
            if (nodeIter.hasNext())
                ans += ",\n";
            else ans += "\n";
        }


        ans+= "]\n" +
                '}';
        return ans;
    }
}