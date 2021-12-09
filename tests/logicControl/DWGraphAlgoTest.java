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
        this.algo.load("Data/G3.json");

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
        Node center = (Node) this.algo.center();
        assertTrue(center.getKey() == 17);

        this.algo.load("Data/1000Nodes.json");
        center = (Node) this.algo.center();
    }

    @Test
    void tsp() {
        List<NodeData> tspTR = new LinkedList<>();
        NodeData n1 = new Node((Node) algo.getGraph().getNode(1));
        NodeData n2 = new Node((Node) algo.getGraph().getNode(2));
        NodeData n3 = new Node((Node) algo.getGraph().getNode(3));
        NodeData n4 = new Node((Node) algo.getGraph().getNode(4));
        tspTR.add(n1);
        tspTR.add(n2);
        tspTR.add(n3);
        tspTR.add(n4);
        List<NodeData> ans = algo.tsp(tspTR);
    }

}