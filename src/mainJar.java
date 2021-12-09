import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import gui.MainFrame;
import logicControl.DWGraph;
import logicControl.DWGraphAlgo;
import org.json.simple.parser.ParseException;

import java.io.IOException;



public class mainJar {
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = new DWGraphAlgo();
        ans.load(json_file);
        return ans;
    }

    public static void main(String[] args) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(args[0]);
        new MainFrame(alg);
    }
}
