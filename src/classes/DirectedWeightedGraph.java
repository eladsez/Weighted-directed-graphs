package classes;
import api.EdgeData;
import api.NodeData;

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
        Collection<NodeData> tempColl = this.Nodes.values();//converts the Node hashmap into Collection
        Iterator<NodeData> iter = tempColl.iterator();//create Iterator for the above collection
        return iter;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        List<EdgeData> list = new ArrayList<>();
        this.Edges.forEach((src, HashMap) -> {
            Object[] tempO = HashMap.values().toArray();
            EdgeData[] temp = new EdgeData[tempO.length];
            for (int i = 0; i < tempO.length; i++)
                temp[i] = (EdgeData)tempO[i];
            Collections.addAll(list, temp);
        });
        Iterator<EdgeData> iter = list.iterator();
        return iter;
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
    public static void main(String[] args) {
        classes.NodeData node1 = new classes.NodeData(1,22);
        classes.NodeData node2 = new classes.NodeData(2,55);
        classes.NodeData node3 = new classes.NodeData(3,65);
        classes.NodeData node4 = new classes.NodeData(4,85);
        classes.NodeData node5 = new classes.NodeData(5,102);
        DirectedWeightedGraph dwg = new DirectedWeightedGraph();
        dwg.addNode(node1);
        dwg.addNode(node2);
        dwg.addNode(node3);
        dwg.addNode(node4);
        dwg.addNode(node5);
        dwg.connect(1,2,5);
        dwg.connect(1,5,1);
        dwg.connect(1,3,2);
        dwg.connect(2,4,6);
//        System.out.println(dwg.edgeSize);
//        dwg.removeNode(1);
//        System.out.println(dwg.edgeSize);
        Iterator<EdgeData> iter = dwg.edgeIter();
        while (iter.hasNext())
            System.out.println(iter.next().getWeight());


    }


}