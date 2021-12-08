package logicControl;

import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.Iterator;

public class TestMain {
    public static void main(String[] args) throws IOException, ParseException {
        DWGraphAlgo algo = new DWGraphAlgo();
        algo.load("Data//G1.json");
        System.out.println(algo.center());
    }
}
