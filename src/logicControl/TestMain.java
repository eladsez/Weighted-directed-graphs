package logicControl;

import api.NodeData;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class TestMain {
    public static void main(String[] args) throws IOException, ParseException {
        DWGraphAlgo algo = new DWGraphAlgo();
        algo.load("Data//G1.json");
        NodeData n1 = (NodeData) algo.getGraph().getNode(1);
        NodeData n2 = (NodeData) algo.getGraph().getNode(2);
        NodeData n3 = (NodeData) algo.getGraph().getNode(3);
        NodeData n4 = (NodeData) algo.getGraph().getNode(4);
        NodeData n5 = (NodeData) algo.getGraph().getNode(5);
        List<NodeData> nodes = new LinkedList<>();
        nodes.add(n1);
        nodes.add(n2);
        nodes.add(n3);
        nodes.add(n4);
        nodes.add(n5);
        System.out.println((algo.tsptry(nodes)).toString());
    }
}
