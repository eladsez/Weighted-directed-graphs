package logicControl;

import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.Iterator;

public class TestMain {
    public static void main(String[] args) throws IOException, ParseException {
        DWGraph g1 = new DWGraph();

        for (int i = 0; i < 100_000; i++)
            g1.addNode(new Node(i, new GeoLocation(0,0,0)));

        for (int i = 1; i < 100_000; i++)
            g1.connect(i-1, i, 0.5);

        DWGraphAlgo algo = new DWGraphAlgo();
        algo.init(g1);
        System.out.println(algo.isConnected());



    }
}
