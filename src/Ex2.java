import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;
import gui.MainFrame;
import logicControl.DWGraph;
import logicControl.DWGraphAlgo;
import logicControl.Node;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph ans = new DWGraph();
        try {
            ((DWGraph)ans).initFromFile(json_file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ans;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = new DWGraphAlgo();
        ans.load(json_file);
        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        new MainFrame(alg);
    }

    public static void main(String[] args) {
        DirectedWeightedGraphAlgorithms algo = getGrapgAlgo("Data/G1.json");
        List<NodeData> tspTR = new LinkedList<>();
        NodeData n1 = new Node((Node) algo.getGraph().getNode(1));
        NodeData n2 = new Node((Node) algo.getGraph().getNode(2));
        NodeData n3 = new Node((Node) algo.getGraph().getNode(4));
        NodeData n4 = new Node((Node) algo.getGraph().getNode(3));
        tspTR.add(n1);
        tspTR.add(n2);
        tspTR.add(n3);
        tspTR.add(n4);
        List<NodeData> ans = algo.tsp(tspTR);
        System.out.println(ans.toString());
//        runGUI("Data/G1.json");
    }
}