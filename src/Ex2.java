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
//        DirectedWeightedGraphAlgorithms a = getGrapgAlgo("Data/G1.json");
//        List<NodeData> tspTR = new LinkedList<>();
//        NodeData n1 = new Node((Node) a.getGraph().getNode(1));
//        NodeData n2 = new Node((Node) a.getGraph().getNode(2));
//        NodeData n3 = new Node((Node) a.getGraph().getNode(3));
//        NodeData n4 = new Node((Node) a.getGraph().getNode(4));
//        NodeData n5 = new Node((Node) a.getGraph().getNode(5));
//        NodeData n6 = new Node((Node) a.getGraph().getNode(6));
//        NodeData n7 = new Node((Node) a.getGraph().getNode(7));
//        NodeData n8 = new Node((Node) a.getGraph().getNode(8));
//        NodeData n9 = new Node((Node) a.getGraph().getNode(9));
//        NodeData n10 = new Node((Node) a.getGraph().getNode(10));
//        NodeData n11 = new Node((Node) a.getGraph().getNode(11));
//        NodeData n12 = new Node((Node) a.getGraph().getNode(12));
//        NodeData n13 = new Node((Node) a.getGraph().getNode(13));
//        NodeData n14 = new Node((Node) a.getGraph().getNode(14));
//        NodeData n15 = new Node((Node) a.getGraph().getNode(15));
//        tspTR.add(n1);
//        tspTR.add(n2);
//        tspTR.add(n3);
//        tspTR.add(n4);
//        tspTR.add(n5);
//        tspTR.add(n6);
//        tspTR.add(n7);
//        tspTR.add(n8);
//        tspTR.add(n9);
//        tspTR.add(n10);
//        tspTR.add(n11);
//        tspTR.add(n12);
//        tspTR.add(n13);
//        tspTR.add(n14);
//        tspTR.add(n15);
//        List<NodeData> ans = a.tsp(tspTR);
//        System.out.println(ans.toString());
        runGUI("Data/G1.json");
    }

}