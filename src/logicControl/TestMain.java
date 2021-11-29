package logicControl;

import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.Iterator;

public class TestMain {
    public static void bla(Integer x){
        x = Integer.valueOf(5);
    }
    public static void main(String[] args) throws IOException, ParseException {
        DirectedWeightedGraph g1 = new logicControl.DirectedWeightedGraph();
        NodeData n0 = new NodeData(0, new GeoLocation(0,0,0));
        NodeData n1 = new NodeData(1, new GeoLocation(0,0,0));
        NodeData n2 = new NodeData(2, new GeoLocation(0,0,0));
        NodeData n3 = new NodeData(3, new GeoLocation(0,0,0));
        g1.addNode(n0);
        g1.addNode(n1);
        g1.addNode(n2);
        g1.addNode(n3);
        g1.connect(0, 1, 0);
        g1.connect(0, 3, 0);
        g1.connect(3, 2, 0);
        g1.connect(1, 2, 0);
        g1.connect(2, 0, 0);
//        g1.initFromFile("Data//G1.json");
//        System.out.println(g1.edgeSize());
//        System.out.println(g1.nodeSize());
//        Integer x = Integer.valueOf(3);
//        bla(x);
//        System.out.println(x);
//        Iterator iter = g1.edgeIter(0);
//        while (iter.hasNext()){
//            System.out.println(((EdgeData)iter.next()).getWeight());
//        }
//        Iterator iter = g1.nodeIter();
//        g1.removeNode(0);
//        while (iter.hasNext())
//            System.out.println(iter.next());
//        Iterator iter = g1.edgeIter();
//        g1.removeNode(0);
//        while (iter.hasNext())
//            System.out.println(iter.next());
        DirectedWeightedGraphAlgorithms DWA = new DirectedWeightedGraphAlgorithms();
        DWA.init(g1);
        System.out.println(DWA.isConnected());
        System.out.println(DWA.counter);


    }
}
