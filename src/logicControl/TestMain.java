package logicControl;

import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.Iterator;

public class TestMain {
    public static void main(String[] args) throws IOException, ParseException {
        DWGraph g1 = new DWGraph();
        Node n0 = new Node(0, new GeoLocation(0,0,0));
        Node n1 = new Node(1, new GeoLocation(0,0,0));
        Node n2 = new Node(2, new GeoLocation(0,0,0));
        Node n3 = new Node(3, new GeoLocation(0,0,0));
        Node n4 = new Node(4, new GeoLocation(0,0,0));
        Node n5 = new Node(5, new GeoLocation(0,0,0));
        g1.addNode(n0);
        g1.addNode(n1);
        g1.addNode(n2);
        g1.addNode(n3);
        g1.addNode(n4);
        g1.addNode(n5);
        g1.connect(0,1,1);
        g1.connect(1,2,1);
        g1.connect(2,3,1);
        g1.connect(3,4,1);
        g1.connect(4,0,1);
        g1.connect(5,0,1);
        g1.connect(5,1,1);
        g1.connect(5,2,1);
        g1.connect(5,3,1);
        g1.connect(5,4,1);


        DWGraphAlgo algo = new DWGraphAlgo();
        algo.init(g1);
    }
}
