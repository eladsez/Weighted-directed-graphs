package classesTests;

import logicControl.DWGraph;
import logicControl.GeoLocation;
import logicControl.Node;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    public DWGraph graph;
    public Node node;

    @BeforeEach
    void init(){
        this.graph = new DWGraph();
        try {
            this.graph.initFromFile("data/G1.json");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.node = new Node(this.graph.getNode(0).getKey(), this.graph.getNode(0).getPos());
    }

    @Test
    void getKey() {
        Assertions.assertTrue(this.node.getKey() == 0);

    }

    @Test
    void getPos() {
        Assertions.assertEquals(this.node.getPos().x(), 35.19589389346247);
        Assertions.assertEquals(this.node.getPos().y(), 32.10152879327731);
        Assertions.assertEquals(this.node.getPos().z(), 0.0);

    }

    @Test
    void setPos() {
        GeoLocation g = new GeoLocation(1.0,2.0,3.0);
        this.node.setPos(g);
        Assertions.assertEquals(this.node.getPos().x(), 1.0);
        Assertions.assertEquals(this.node.getPos().y(), 2.0);
        Assertions.assertEquals(this.node.getPos().z(), 3.0);

    }

}