package classesTests;

import logicControl.DWGraph;
import logicControl.Edge;
import logicControl.Node;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
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
        this.edge = new Edge(0, 16, 1.3118716362419698);
    }

    @Test
    void getSrc() {
        assertTrue(this.edge.getSrc() == 0);
    }

    @Test
    void getDest() {
        assertTrue(this.edge.getDest() == 16);
    }

    @Test
    void getWeight() {
        assertTrue(this.edge.getWeight() == 1.3118716362419698);
    }

}