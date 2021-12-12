package logicControl;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGraphAlgoTest {
    DWGraphAlgo algo;

    @BeforeEach
    void init() {
        this.algo = new DWGraphAlgo();
        this.algo.load("Data/G1.json");
//        this.algo.load("Data/G2.json");
//        this.algo.load("Data/G3.json");
//        this.algo.load("Data/1000Nodes.json");
//        this.algo.load("Data/10000Nodes.json");

    }


    @Test
    void isConnected() {
        assertTrue(this.algo.isConnected());
    }

    @Test
    void shortestPathDist() {
        Iterator iter1 = this.algo.getGraph().nodeIter();
        Iterator iter2 = this.algo.getGraph().nodeIter();
        double dist = Double.MAX_VALUE;
        Node curr, to;

        while (iter1.hasNext()){
            curr = (Node) iter1.next();
            while (iter2.hasNext()){
                to = (Node) iter2.next();
                dist = this.algo.shortestPathDist(curr.getKey(), to.getKey());
            }
        }
    }

    @Test
    void shortestPath() {
        Iterator iter1 = this.algo.getGraph().nodeIter();
        Iterator iter2 = this.algo.getGraph().nodeIter();
        List<NodeData> path = new LinkedList<>();
        Node curr, to;

        while (iter1.hasNext()){
            curr = (Node) iter1.next();
            while (iter2.hasNext()){
                to = (Node) iter2.next();
                path.addAll(this.algo.shortestPath(curr.getKey(), to.getKey()));
            }
        }
    }

    @Test
    void center() {
        this.algo.center();
    }

    @Test
    void tsp() {
        List<NodeData> tspTR = new LinkedList<>();
        NodeData n1 = new Node((Node) algo.getGraph().getNode(1));
        NodeData n2 = new Node((Node) algo.getGraph().getNode(2));
        NodeData n3 = new Node((Node) algo.getGraph().getNode(3));
        NodeData n4 = new Node((Node) algo.getGraph().getNode(4));
        NodeData n5 = new Node((Node) algo.getGraph().getNode(5));
        NodeData n6 = new Node((Node) algo.getGraph().getNode(6));
        NodeData n7 = new Node((Node) algo.getGraph().getNode(7));
        NodeData n8 = new Node((Node) algo.getGraph().getNode(8));
        NodeData n9 = new Node((Node) algo.getGraph().getNode(9));
        NodeData n10 = new Node((Node) algo.getGraph().getNode(10));
//        NodeData n11 = new Node((Node) algo.getGraph().getNode(11));
//        NodeData n12 = new Node((Node) algo.getGraph().getNode(12));
//        NodeData n13 = new Node((Node) algo.getGraph().getNode(13));
//        NodeData n14 = new Node((Node) algo.getGraph().getNode(14));
//        NodeData n15 = new Node((Node) algo.getGraph().getNode(15));
        tspTR.add(n1);
        tspTR.add(n2);
        tspTR.add(n3);
        tspTR.add(n4);
        tspTR.add(n5);
        tspTR.add(n6);
        tspTR.add(n7);
        tspTR.add(n8);
        tspTR.add(n9);
        tspTR.add(n10);
//        tspTR.add(n11);
//        tspTR.add(n12);
//        tspTR.add(n13);
//        tspTR.add(n14);
//        tspTR.add(n15);
        List<NodeData> ans = algo.tsp(tspTR);
    }
}