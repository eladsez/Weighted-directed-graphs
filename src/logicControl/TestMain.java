package logicControl;

import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.Iterator;

public class TestMain {
    public static void main(String[] args) throws IOException, ParseException {
        DirectedWeightedGraph g1 = new logicControl.DirectedWeightedGraph();
        g1.initFromFile("Data//G1.json");
        System.out.println(g1.edgeSize());
        System.out.println(g1.nodeSize());
//        Iterator iter = g1.nodeIter();
//        g1.removeNode(0);
//        while (iter.hasNext())
//            System.out.println(iter.next());
//        Iterator iter = g1.edgeIter();
//        g1.removeNode(0);
//        while (iter.hasNext())
//            System.out.println(iter.next());
    }
}
