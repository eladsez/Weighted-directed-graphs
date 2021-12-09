package classesTests;

import api.NodeData;
import logicControl.DWGraph;
import logicControl.Edge;
import logicControl.Node;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class DWGraphTest {
    public DWGraph graph;
    public Edge edge;

    @BeforeEach
    void init() {
        this.graph = new DWGraph();
        try {
            this.graph.initFromFile("data/G1.json");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void initTest() throws IOException, ParseException {
       DWGraph g1 = new DWGraph();
       DWGraph g2 = new DWGraph();
       DWGraph g3 = new DWGraph();
       DWGraph g4 = new DWGraph();
       g1.initFromFile("Data/G1.json");
       g2.initFromFile("Data/G2.json");
       g3.initFromFile("Data/G3.json");
       g4 = this.graph;
       g4.initFromFile("Data/G2.json");

    }

    @Test
    void getNode() {
        Node node = (Node) this.graph.getNode(0);
        assertTrue(node instanceof NodeData);
        assertTrue(node.getKey() == 0);
        assertTrue(node.getPos().x() == 35.19589389346247);
        assertTrue(node.getPos().y() == 32.10152879327731);
        assertTrue(node.getPos().z() == 0.0);

    }

    @Test
    void getEdge() {
        Edge edge = (Edge) this.graph.getEdge(2, 1);
        assertTrue(edge instanceof Edge);
        assertEquals(edge.getWeight(),1.5784991011275615);

    }


    @Test
    void connect() {
        this.graph.connect(6,9, 7);
        assertEquals(this.graph.getEdge(6,9).getWeight(), 7);
    }

    @Test
    void nodeIter() {
        Iterator iter = this.graph.nodeIter();
        assertTrue(iter instanceof Iterator);
    }

    @Test
    void edgeIter() {
        Iterator iter = this.graph.edgeIter();
        assertTrue(iter instanceof Iterator);
    }

}