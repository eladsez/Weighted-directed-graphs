package logicControl;

import org.json.simple.parser.ParseException;
import java.io.IOException;

public class TestMain {
    public static void bla(Integer x){
        x = Integer.valueOf(5);
    }
    public static void main(String[] args) throws IOException, ParseException {
        DWGraph g1 = new DWGraph();
        Node n0 = new Node(0, new GeoLocation(0,0,0));
        Node n1 = new Node(1, new GeoLocation(0,0,0));
        Node n2 = new Node(2, new GeoLocation(0,0,0));
        Node n3 = new Node(3, new GeoLocation(0,0,0));
        g1.addNode(n0);
        g1.addNode(n1);
        g1.addNode(n2);
        g1.addNode(n3);
        g1.connect(0, 1, 0);
        g1.connect(1, 2, 0);
        g1.connect(2, 0, 0);
        g1.connect(3, 1, 0);
        g1.connect(1, 3, 0);
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
        DWGraphAlgo DWA = new DWGraphAlgo();
        DWA.init(g1);
        System.out.println(DWA.isConnected());
    }
}
